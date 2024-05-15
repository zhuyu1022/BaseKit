package com.zhuyu.basekit.ext

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import java.io.File
import java.util.concurrent.atomic.AtomicInteger
import kotlin.io.path.pathString

/**
 * 二注册ActivityResultLauncher必须在Activity#onStart()之前?
 * 答案：当然不是
 * 通过源码我可以知道，实际上注册获取ActivityResultLauncher是通过ActivityResultRegistry注册，通过查看源码然后我发现还有一个注册方法是不需要LifecycleOwner。
 * 这样我们就可以在任意一个地方只要有Activity的上下文（context）就可以注册获取ActivityResultLauncher。
 * 首先通过context(需要注意这里的context，要判断是否ComponentActivity) androidx.activity.ComponentActivity#getActivityResultRegistry获取ActivityResultRegistry 然后通过下面的方法进行注册：
 * 于是添加了下面的扩展方法，进行注册获取ActivityResultLauncher： Activity:
 */
fun <I, O> ComponentActivity.register(
    key: String = "activity_rq#" + AtomicInteger().getAndAdd(1),
    contract: ActivityResultContract<I, O>,
    callback: ActivityResultCallback<O>
): ActivityResultLauncher<I> {
    return activityResultRegistry.register(key, contract, callback).also {
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    it.unregister()
                }
            }
        })
    }
}

/**
 * 启动一个intent
 */
fun ComponentActivity.registerIntent(
    key: String = "activity_rq#" + AtomicInteger().getAndAdd(1),
    intent: Intent,
    callback: ActivityResultCallback<ActivityResult>
) {
    return register(key, ActivityResultContracts.StartActivityForResult(), callback).also {
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    it.unregister()
                }
            }
        })
    }.launch(intent)
}

fun <I, O> Fragment.register(
    key: String = "activity_rq#" + AtomicInteger().getAndAdd(1),
    contract: ActivityResultContract<I, O>,
    callback: ActivityResultCallback<O>
): ActivityResultLauncher<I> {
    return requireActivity().activityResultRegistry.register(key, contract, callback).also {
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    it.unregister()
                }
            }
        })
    }
}

/**
 * 启动一个intent
 */
fun Fragment.registerIntent(
    key: String = "activity_rq#" + AtomicInteger().getAndAdd(1),
    intent: Intent,
    callback: ActivityResultCallback<ActivityResult>
) {
    return register(key, ActivityResultContracts.StartActivityForResult(), callback).also {
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    it.unregister()
                }
            }
        })
    }.launch(intent)
}

/**
 * 自带的ActivityResultContract：
 * StartActivityForResult 使用Intent在activity（fragment）间通信，返回ActivityResult
 * StartIntentSenderForResult 使用IntentSenderRequest. builder构造，可以返回带有activity的ActivityResult
 * RequestMultiplePermissions 获取多个动态权限，返回Map<String,boolean>数组
 * RequestPermission 获取单个动态权限，成功后返回true
 * TakePicturePreview 调用相机，拍照后返回bitmap图片信息
 * TakePicture 调用相机，拍照后将图片保存到开发者指定的Uri，返回true
 * TakeVideo 拍摄视频，将拍摄内容保存到开发者指定的Uri，返回缩略图
 * CaptureVideo 拍摄视频，将内容保存到开发者指定的Uri后，返回true
 * PickContact 弹出手机联系人列表，用户选中其中一个后，返回Uri
 * GetContent 访问原始数据，返回Uri，例如相册中的单个图片
 * GetMultipleContents 功能如上，返回Uri数组，例如相册中的多个图片
 * OpenDocument 访问文件数据，打开文件夹，返回单个文件的Uri
 * OpenMultipleDocuments 功能如上，返回多个文件的Uri
 * OpenDocumentTree
 * CreateDocument 创建文件
 */



/***********************************************以下封装了一些系统自带的ActivityResultContract***********************************************************/
/**
 * 拍照
 * takePicture
 */
fun ComponentActivity.takePicture( onResult: (uri: Uri) -> Unit) {
    val pictureFile = kotlin.io.path.createTempFile(System.currentTimeMillis().toString(), ".jpg")
    val uri = FileProvider.getUriForFile(this, this.packageName + ".provider", File(pictureFile.pathString))
    register(contract = ActivityResultContracts.TakePicture(), callback = { success ->
        if (success) {
            onResult(uri)
        }
    }).launch(uri)
}

/**
 * 拍照
 * 给定一个uri
 */
fun ComponentActivity.takePicture( uri:Uri,onResult: (uri: Uri) -> Unit) {
    register(contract = ActivityResultContracts.TakePicture(), callback = { success ->
        if (success) {
            onResult(uri)
        }
    }).launch(uri)
}
fun ComponentActivity.takePicturePreview( onResult: (bitamp: Bitmap?) -> Unit) {

    register(contract = ActivityResultContracts.TakePicturePreview(), callback = { bitamp ->
            onResult(bitamp)
    }).launch(null)
}

/**
 * 拍摄视频，将内容保存到开发者指定的Uri
 */
fun ComponentActivity.captureVideo( onResult: (uri: Uri) -> Unit) {
    val pictureFile = kotlin.io.path.createTempFile(System.currentTimeMillis().toString(), ".mp4")
    val uri = FileProvider.getUriForFile(this, this.packageName + ".provider", File(pictureFile.pathString))
    register(contract = ActivityResultContracts.CaptureVideo(), callback = { success ->
        if (success) {
            onResult(uri)
        }
    }).launch(uri)
}

/**
 * 请求权限
 */
fun ComponentActivity.requestPermission(permission:String,onResult: (isGranted: Boolean) -> Unit){
    register(contract = ActivityResultContracts.RequestPermission(), callback = { isGranted ->
        onResult(isGranted)
    }).launch(permission)
}


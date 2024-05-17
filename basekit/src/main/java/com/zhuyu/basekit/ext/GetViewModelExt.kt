package com.zhuyu.basekit.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import com.zhuyu.basekit.base.viewmodel.BaseViewModel
import java.lang.reflect.ParameterizedType


/**
 * 获取当前类绑定的泛型ViewModel-clazz
 */
@Suppress("UNCHECKED_CAST")
fun <VM> getVmClazz(obj: Any): VM {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
}







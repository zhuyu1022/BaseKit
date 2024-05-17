package com.zhuyu.basekit.base.activity

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.zhuyu.basekit.ext.inflateBindingWithGeneric


abstract class BaseVbActivity< VB : ViewBinding> : AppCompatActivity() {



    lateinit var mViewBind: VB


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initBinding())
        initView(savedInstanceState)
    }
    /**
     * 创建DataBinding
     */
   private  fun initBinding(): View? {
         mViewBind = inflateBindingWithGeneric(layoutInflater)
         return mViewBind.root
    }

    abstract fun initView(savedInstanceState: Bundle?)
    abstract fun showLoading(message: String = "请求网络中...")
    abstract fun dismissLoading()

}
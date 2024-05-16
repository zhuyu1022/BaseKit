package com.zhuyu.basekit.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.viewbinding.ViewBinding
import com.zhuyu.basekit.ext.getClazz


abstract class BaseVbActivity<VB : ViewBinding>: ComponentActivity() {

    lateinit var mViewBind: VB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBind=initBinding()
        setContentView(mViewBind.root)
        initView(savedInstanceState)

    }
    abstract fun initView(savedInstanceState: Bundle?)


    /**
     *  利用反射获取ViewBinding的实例
     */
    private  fun initBinding(): VB {
        return getClazz<VB>(this, 0).getMethod("inflate", LayoutInflater::class.java)
            .invoke(null, layoutInflater) as VB
    }
}
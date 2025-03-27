package com.zhuyu.basekit.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding


/**
 * ViewBinding 对activity的基类
 * *
 * 务必不要在基类里写太多东西
 * @author shixianjie
 */
abstract class BaseBindingActivity<VB: ViewBinding> : AppCompatActivity() {

    val binding: VB by lazy { createBinding() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    abstract fun createBinding(): VB

    open fun initView() {

    }

}
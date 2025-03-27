package com.zhuyu.basekit.base.dialog

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.StyleRes
import androidx.viewbinding.ViewBinding
import com.zhuyu.basekit.R


abstract class BaseBindingDialog<VB: ViewBinding>(
    context: Context,
    @StyleRes theme: Int = R.style.Base_Dialog
) : AlertDialog(context, theme) {

    internal val binding: VB by lazy { createBinding() }
    internal var isDialogCreated = false
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        initWindow()

        initView()
        isDialogCreated = true
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        onDialogStart()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        onDialogStop()
    }

    open fun initWindow() {
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
    }

    open fun initView() {
    }

    open fun onDialogStart() {
    }

    open fun onDialogStop() {
    }

    abstract fun createBinding(): VB

}
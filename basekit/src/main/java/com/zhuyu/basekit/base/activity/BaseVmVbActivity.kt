package com.zhuyu.basekit.base.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.zhuyu.basekit.base.viewmodel.BaseViewModel
import com.zhuyu.basekit.ext.getClazz
import com.zhuyu.basekit.ext.getVmClazz
import com.zhuyu.basekit.ext.inflateBindingWithGeneric


abstract class BaseVmVbActivity<VM : BaseViewModel, VB : ViewBinding> : AppCompatActivity() {
    lateinit var mViewBind: VB
    lateinit var mViewModel: VM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initBinding())
        createViewModel()
        createObserver()
        initView(savedInstanceState)
        registerUiChange()
    }

    private fun createViewModel() {
        mViewModel= ViewModelProvider(this)[getVmClazz(this)]
    }

    private  fun initBinding(): View? {
        mViewBind = inflateBindingWithGeneric(layoutInflater)
        return mViewBind.root
    }

    abstract fun createObserver()
    abstract  fun initView(savedInstanceState: Bundle?)
    abstract fun showLoading(message: String = "请求网络中...")
    abstract fun dismissLoading()
    /**
     * 注册UI 事件
     */
    private fun registerUiChange() {
        //显示弹窗
        mViewModel.loadingChange.showDialog.observe(this) {
            showLoading(it)
        }
        //关闭弹窗
        mViewModel.loadingChange.dismissDialog.observe(this) {
            dismissLoading()
        }
    }

    /**
     * 将非该Activity绑定的ViewModel添加 loading回调 防止出现请求时不显示 loading 弹窗bug
     * @param viewModels Array<out BaseViewModel>
     */
    protected fun addLoadingObserve(vararg viewModels: BaseViewModel) {
        viewModels.forEach { viewModel ->
            //显示弹窗
            viewModel.loadingChange.showDialog.observe(this) {
                showLoading(it)
            }
            //关闭弹窗
            viewModel.loadingChange.dismissDialog.observe(this) {
                dismissLoading()
            }
        }
    }


}
package com.zhuyu.basekit.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.zhuyu.basekit.base.viewmodel.BaseViewModel
import com.zhuyu.basekit.ext.getClazz
import com.zhuyu.basekit.ext.inflateBindingWithGeneric


abstract class BaseVmVbFragment<VM : BaseViewModel, VB : ViewBinding> : Fragment() {


    lateinit var mViewModel: VM

    //该类绑定的 ViewBinding
    private var _binding: VB? = null
    val mViewBind: VB get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBindingWithGeneric(inflater, container, false)
        return mViewBind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createViewModel()
        initView(savedInstanceState)
        createObserver()
        registorDefUIChange()
    }
    /**
     * 初始化view
     */
    abstract fun initView(savedInstanceState: Bundle?)
    abstract fun createObserver()
    abstract fun showLoading(message: String = "请求网络中...")
    abstract fun dismissLoading()
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * 创建viewModel
     */
    private fun createViewModel() {
        mViewModel = ViewModelProvider(this)[getClazz(this)]
        // return ViewModelProvider(this)[getVmClazz(this)]
    }


    /**
     * 注册 UI 事件
     */
    private fun registorDefUIChange() {
        mViewModel.loadingChange.showDialog.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        mViewModel.loadingChange.dismissDialog.observe(viewLifecycleOwner) {
            dismissLoading()
        }
    }

    /**
     * 将非该Fragment绑定的ViewModel添加 loading回调 防止出现请求时不显示 loading 弹窗bug
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
package com.zhuyu.basekit.ui

import android.os.Bundle
import com.zhuyu.basekit.base.fragment.BaseVbFragment
import com.zhuyu.basekit.base.fragment.BaseVmVbFragment
import com.zhuyu.basekit.base.viewmodel.BaseViewModel
import com.zhuyu.basekit.databinding.FragmentBlankBinding

class BlankFragment : BaseVmVbFragment<BaseViewModel,FragmentBlankBinding>() {


    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun createObserver() {

    }

    override fun showLoading(message: String) {

    }

    override fun dismissLoading() {

    }


}
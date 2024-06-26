package com.zhuyu.basekit

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.blankj.utilcode.util.FragmentUtils

import com.zhuyu.basekit.base.activity.BaseVmVbActivity
import com.zhuyu.basekit.base.viewmodel.BaseViewModel
import com.zhuyu.basekit.databinding.ActivityMainBinding
import com.zhuyu.basekit.ext.clickNoRepeat
import com.zhuyu.basekit.ui.BlankFragment
import com.zhuyu.basekit.ui.DownloadActivity
import com.zhuyu.basekit.viewmodel.MainViewModel

class MainActivity : BaseVmVbActivity<MainViewModel,ActivityMainBinding>() {

//val mainViewModel:MainViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {
        mViewBind.downloadBtn.clickNoRepeat {
            startActivity(Intent(this, DownloadActivity::class.java))
        }
        mViewBind.activityResultBtn.clickNoRepeat {
            startActivity(Intent(this, ResultActivity::class.java))
        }
        mViewBind.fragmentBtn.clickNoRepeat {
           // FragmentUtils.add(supportFragmentManager,BlankFragment(),R.id.container)

        }
        //mainViewModel.getBanner()
        mViewModel.getBanner()
    }

    override fun onStart() {
        super.onStart()

    }
    override fun showLoading(message: String) {

    }

    override fun dismissLoading() {

    }

    override fun createObserver() {

    }
}
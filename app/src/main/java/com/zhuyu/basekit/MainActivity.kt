package com.zhuyu.basekit

import android.content.Intent
import android.os.Bundle
import com.zhuyu.basekit.base.activity.BaseVmActivity
import com.zhuyu.basekit.base.viewmodel.BaseViewModel
import com.zhuyu.basekit.databinding.ActivityMainBinding
import com.zhuyu.basekit.ext.clickNoRepeat
import com.zhuyu.basekit.ui.DownloadActivity

class MainActivity : BaseVmActivity<BaseViewModel,ActivityMainBinding>() {



    override fun initView(savedInstanceState: Bundle?) {
        mViewBind.downloadBtn.clickNoRepeat {
            startActivity(Intent(this, DownloadActivity::class.java))
        }
        mViewBind.activityResultBtn.clickNoRepeat {
            startActivity(Intent(this, ResultActivity::class.java))
        }
    }

    override fun showLoading(message: String) {

    }

    override fun dismissLoading() {

    }

    override fun createObserver() {

    }
}
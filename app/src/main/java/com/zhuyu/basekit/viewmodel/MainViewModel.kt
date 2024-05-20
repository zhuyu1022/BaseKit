package com.zhuyu.basekit.viewmodel

import com.zhuyu.basekit.base.viewmodel.BaseViewModel
import com.zhuyu.basekit.ext.request
import com.zhuyu.basekit.net.NetworkApi
import com.zhuyu.basekit.util.LogUtils

class MainViewModel:BaseViewModel() {
    fun getBanner(){
        request(block = {
            NetworkApi.INSTANCE.getApiService().getBanner()
        }, success = {
            LogUtils.debugInfo(it.toString())
        })
    }
}
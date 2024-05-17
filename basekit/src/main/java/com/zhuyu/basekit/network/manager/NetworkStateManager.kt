package com.zhuyu.basekit.network.manager

import androidx.lifecycle.MutableLiveData



/**
 * 作者　: hegaojian
 * 时间　: 2020/5/2
 * 描述　: 网络变化管理者
 */
class NetworkStateManager private constructor() {

    val mNetworkStateCallback = MutableLiveData<NetState>()

    companion object {
        val instance: NetworkStateManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkStateManager()
        }
    }

}
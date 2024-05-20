package com.zhuyu.basekit.net

import me.hgj.jetpackmvvm.network.BaseResponse

data class ApiResponse<T>(val code: Int, val msg: String, val data: T) : BaseResponse<T>() {

    override fun isSucces() = code == 1

    override fun getResponseCode() = code

    override fun getResponseData() = data

    override fun getResponseMsg() = msg



}
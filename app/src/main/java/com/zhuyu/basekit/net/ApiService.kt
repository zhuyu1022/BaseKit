package com.zhuyu.basekit.net




import com.zhuyu.basekit.data.BannerResponse
import okhttp3.MultipartBody
import retrofit2.http.*


interface ApiService {



//    @POST("/banner/json")
//    @FormUrlEncoded
//    suspend fun getBanner(@FieldMap params: Map<String, String>): ApiResponse<BannerResponse>
    @POST("/banner/json")
    suspend fun getBanner(): ApiResponse<BannerResponse>
}
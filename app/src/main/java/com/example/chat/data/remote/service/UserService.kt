package com.example.chat.data.remote.service

import com.example.chat.data.remote.model.response.BaseResponse
import com.example.chat.data.remote.model.response.UserResponse
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserService {

    @FormUrlEncoded
    @POST("login.php")
    fun login(@FieldMap params: Map<String, String>): Call<UserResponse>

    @FormUrlEncoded
    @POST("register.php")
    fun register(@FieldMap params: Map<String, String>): Call<BaseResponse>

    @FormUrlEncoded
    @POST("editUser.php")
    fun editUser(@FieldMap params: Map<String, String>): Call<UserResponse>

    @FormUrlEncoded
    @POST("updateUserToken.php")
    fun updateToken(@FieldMap params: Map<String, String>): Call<BaseResponse>

    @FormUrlEncoded
    @POST("forgetPassword.php")
    fun forgetPassword(@FieldMap params: Map<String, String>): Call<BaseResponse>

    @FormUrlEncoded
    @POST("updateUserLastSeen.php")
    fun updateUserLastSeen(@FieldMap params: Map<String, String>): Call<BaseResponse>
}
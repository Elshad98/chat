package com.example.chat.data.remote.service

import com.example.chat.data.remote.model.response.BaseResponse
import com.example.chat.data.remote.model.response.UserResponse
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserService {

    companion object {

        const val PARAM_EMAIL = "email"
        const val PARAM_NAME = "name"
        const val PARAM_USER_ID = "user_id"
        const val PARAM_FRIENDS_ID = "friends_id"
        const val PARAM_STATUS = "status"
        const val PARAM_REQUEST_USER = "request_user"
        const val PARAM_APPROVED_USER = "approved_user"
        const val PARAM_IMAGE = "image"

        const val PARAM_MESSAGE = "message"
        const val PARAM_MESSAGE_TYPE = "message_type_id"
        const val PARAM_CONTACT_ID = "contact_id"

        const val PARAM_SENDER_USER = "senderUser"
        const val PARAM_SENDER_USER_ID = "senderUserId"
        const val PARAM_RECEIVED_USER_ID = "receivedUserId"
        const val PARAM_MESSAGE_ID = "message_id"

        const val PARAM_LAST_SEEN = "last_seen"
    }

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
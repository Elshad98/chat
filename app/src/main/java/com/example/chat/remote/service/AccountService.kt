package com.example.chat.remote.service

import com.example.chat.remote.account.AuthResponse
import com.example.chat.remote.core.BaseResponse
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AccountService {

    companion object {

        const val PARAM_EMAIL = "email"
        const val PARAM_PASSWORD = "password"
        const val PARAM_NAME = "name"
        const val PARAM_TOKEN = "token"
        const val PARAM_USER_DATE = "user_date"
        const val PARAM_USER_ID = "user_id"
        const val PARAM_OLD_TOKEN = "old_token"
        const val PARAM_REQUEST_USER_ID = "request_user_id"
        const val PARAM_FRIENDS_ID = "friends_id"
        const val PARAM_STATUS = "status"
        const val PARAM_REQUEST_USER = "request_user"
        const val PARAM_APPROVED_USER = "approved_user"
        const val PARAM_IMAGE_NEW = "image_new"
        const val PARAM_IMAGE_NEW_NAME = "image_new_name"
        const val PARAM_IMAGE_UPLOADED = "image_uploaded"
        const val PARAM_IMAGE = "image"

        const val PARAM_SENDER_ID = "sender_id"
        const val PARAM_RECEIVER_ID = "receiver_id"
        const val PARAM_MESSAGE = "message"
        const val PARAM_MESSAGE_TYPE = "message_type_id"
        const val PARAM_MESSAGE_DATE = "message_date"
        const val PARAM_CONTACT_ID = "contact_id"

        const val PARAM_SENDER_USER = "senderUser"
        const val PARAM_SENDER_USER_ID = "senderUserId"
        const val PARAM_RECEIVED_USER_ID = "receivedUserId"
        const val PARAM_MESSAGE_ID = "message_id"
        const val PARAM_MESSAGES_IDS = "messages_ids"

        const val PARAM_LAST_SEEN = "last_seen"
    }

    @FormUrlEncoded
    @POST("login.php")
    fun login(@FieldMap params: Map<String, String>): Call<AuthResponse>

    @FormUrlEncoded
    @POST("register.php")
    fun register(@FieldMap params: Map<String, String>): Call<BaseResponse>

    @FormUrlEncoded
    @POST("editUser.php")
    fun editUser(@FieldMap params: Map<String, String>): Call<AuthResponse>

    @FormUrlEncoded
    @POST("updateUserToken.php")
    fun updateToken(@FieldMap params: Map<String, String>): Call<BaseResponse>

    @FormUrlEncoded
    @POST("forget_password.php")
    fun forgetPassword(@FieldMap params: Map<String, String>): Call<BaseResponse>

    @FormUrlEncoded
    @POST("updateUserLastSeen.php")
    fun updateUserLastSeen(@FieldMap params: Map<String, String>): Call<BaseResponse>
}
package com.example.chat.remote.service

import com.example.chat.remote.account.AuthResponse
import com.example.chat.remote.core.BaseResponse
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    companion object {

        // Methods
        const val REGISTER = "register.php"
        const val LOGIN = "login.php"
        const val UPDATE_TOKEN = "updateUserToken.php"

        // Params
        const val PARAM_EMAIL = "email"
        const val PARAM_PASSWORD = "password"
        const val PARAM_NAME = "name"
        const val PARAM_TOKEN = "token"
        const val PARAM_USER_DATE = "user_date"
        const val PARAM_USER_ID = "user_id"
        const val PARAM_OLD_TOKEN = "old_token"
    }

    @FormUrlEncoded
    @POST(REGISTER)
    fun register(@FieldMap params: Map<String, String>): Call<BaseResponse>

    @FormUrlEncoded
    @POST(LOGIN)
    fun login(@FieldMap params: Map<String, String>): Call<AuthResponse>

    @FormUrlEncoded
    @POST(UPDATE_TOKEN)
    fun updateToken(@FieldMap params: Map<String, String>): Call<BaseResponse>
}
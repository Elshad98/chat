package com.example.chat.data.remote.service

import com.example.chat.data.remote.model.response.BaseResponse
import com.example.chat.data.remote.model.response.MessagesResponse
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MessageService {

    @FormUrlEncoded
    @POST("sendMessage.php")
    fun sendMessage(@FieldMap params: Map<String, String>): Call<BaseResponse>

    @FormUrlEncoded
    @POST("deleteMessagesByUser.php")
    fun deleteMessagesByUser(@FieldMap params: Map<String, String>): Call<BaseResponse>

    @FormUrlEncoded
    @POST("getLastMessagesByUser.php")
    fun getLastMessages(@FieldMap params: Map<String, String>): Call<MessagesResponse>

    @FormUrlEncoded
    @POST("getMessagesByUserWithContact.php")
    fun getMessagesWithContact(@FieldMap params: Map<String, String>): Call<MessagesResponse>
}
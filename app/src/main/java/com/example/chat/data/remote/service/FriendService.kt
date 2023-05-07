package com.example.chat.data.remote.service

import com.example.chat.data.remote.model.response.BaseResponse
import com.example.chat.data.remote.model.response.FriendRequestsResponse
import com.example.chat.data.remote.model.response.FriendsResponse
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface FriendService {

    @FormUrlEncoded
    @POST("addFriend.php")
    fun addFriend(@FieldMap params: Map<String, String>): Call<BaseResponse>

    @FormUrlEncoded
    @POST("removeFriend.php")
    fun removeFriend(@FieldMap params: Map<String, String>): Call<BaseResponse>

    @FormUrlEncoded
    @POST("getContactsByUser.php")
    fun getFriends(@FieldMap params: Map<String, String>): Call<FriendsResponse>

    @FormUrlEncoded
    @POST("cancelFriendRequest.php")
    fun cancelFriendRequest(@FieldMap params: Map<String, String>): Call<BaseResponse>

    @FormUrlEncoded
    @POST("approveFriendRequest.php")
    fun approveFriendRequest(@FieldMap params: Map<String, String>): Call<BaseResponse>

    @FormUrlEncoded
    @POST("getFriendRequestsByUser.php")
    fun getFriendRequests(@FieldMap params: Map<String, String>): Call<FriendRequestsResponse>
}
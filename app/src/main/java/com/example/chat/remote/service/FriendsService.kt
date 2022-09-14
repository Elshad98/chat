package com.example.chat.remote.service

import com.example.chat.remote.core.BaseResponse
import com.example.chat.remote.friends.GetFriendRequestsResponse
import com.example.chat.remote.friends.GetFriendsResponse
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface FriendsService {

    @FormUrlEncoded
    @POST("addFriend.php")
    fun addFriend(@FieldMap params: Map<String, String>): Call<BaseResponse>

    @FormUrlEncoded
    @POST("deleteFriend.php")
    fun deleteFriend(@FieldMap params: Map<String, String>): Call<BaseResponse>

    @FormUrlEncoded
    @POST("getContactsByUser.php")
    fun getFriends(@FieldMap params: Map<String, String>): Call<GetFriendsResponse>

    @FormUrlEncoded
    @POST("cancelFriendRequest.php")
    fun cancelFriendRequest(@FieldMap params: Map<String, String>): Call<BaseResponse>

    @FormUrlEncoded
    @POST("approveFriendRequest.php")
    fun approveFriendRequest(@FieldMap params: Map<String, String>): Call<BaseResponse>

    @FormUrlEncoded
    @POST("getFriendRequestsByUser.php")
    fun getFriendRequests(@FieldMap params: Map<String, String>): Call<GetFriendRequestsResponse>
}
package com.example.chat.remote.service

import com.example.chat.remote.account.AuthResponse
import com.example.chat.remote.core.BaseResponse
import com.example.chat.remote.friends.GetFriendRequestsResponse
import com.example.chat.remote.friends.GetFriendsResponse
import com.example.chat.remote.messages.GetMessagesResponse
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    companion object {

        private const val LOGIN = "login.php"
        private const val REGISTER = "register.php"
        private const val EDIT_USER = "editUser.php"
        private const val ADD_FRIEND = "addFriend.php"
        private const val SEND_MESSAGE = "sendMessage.php"
        private const val DELETE_FRIEND = "deleteFriend.php"
        private const val UPDATE_TOKEN = "updateUserToken.php"
        private const val GET_FRIENDS = "getContactsByUser.php"
        private const val GET_LAST_MESSAGES = "getLastMessagesByUser.php"
        private const val UPDATE_USER_LAST_SEEN = "updateUserLastSeen.php"
        private const val CANCEL_FRIEND_REQUEST = "cancelFriendRequest.php"
        private const val GET_FRIEND_REQUESTS = "getFriendRequestsByUser.php"
        private const val APPROVE_FRIEND_REQUEST = "approveFriendRequest.php"
        private const val DELETE_MESSAGES_BY_USER = "deleteMessagesByUser.php"
        private const val GET_MESSAGES_WITH_CONTACT = "getMessagesByUserWithContact.php"

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
    @POST(REGISTER)
    fun register(@FieldMap params: Map<String, String>): Call<BaseResponse>

    @FormUrlEncoded
    @POST(LOGIN)
    fun login(@FieldMap params: Map<String, String>): Call<AuthResponse>

    @FormUrlEncoded
    @POST(UPDATE_TOKEN)
    fun updateToken(@FieldMap params: Map<String, String>): Call<BaseResponse>

    @FormUrlEncoded
    @POST(ADD_FRIEND)
    fun addFriend(@FieldMap params: Map<String, String>): Call<BaseResponse>

    @FormUrlEncoded
    @POST(APPROVE_FRIEND_REQUEST)
    fun approveFriendRequest(@FieldMap params: Map<String, String>): Call<BaseResponse>

    @FormUrlEncoded
    @POST(CANCEL_FRIEND_REQUEST)
    fun cancelFriendRequest(@FieldMap params: Map<String, String>): Call<BaseResponse>

    @FormUrlEncoded
    @POST(DELETE_FRIEND)
    fun deleteFriend(@FieldMap params: Map<String, String>): Call<BaseResponse>

    @FormUrlEncoded
    @POST(GET_FRIENDS)
    fun getFriends(@FieldMap params: Map<String, String>): Call<GetFriendsResponse>

    @FormUrlEncoded
    @POST(GET_FRIEND_REQUESTS)
    fun getFriendRequests(@FieldMap params: Map<String, String>): Call<GetFriendRequestsResponse>

    @FormUrlEncoded
    @POST(EDIT_USER)
    fun editUser(@FieldMap params: Map<String, String>): Call<AuthResponse>

    @FormUrlEncoded
    @POST(SEND_MESSAGE)
    fun sendMessages(@FieldMap params: Map<String, String>): Call<BaseResponse>

    @FormUrlEncoded
    @POST(GET_LAST_MESSAGES)
    fun getLastMessages(@FieldMap params: Map<String, String>): Call<GetMessagesResponse>

    @FormUrlEncoded
    @POST(GET_MESSAGES_WITH_CONTACT)
    fun getMessagesWithContact(@FieldMap params: Map<String, String>): Call<GetMessagesResponse>

    @FormUrlEncoded
    @POST(DELETE_MESSAGES_BY_USER)
    fun deleteMessagesByUser(@FieldMap params: Map<String, String>): Call<BaseResponse>

    @FormUrlEncoded
    @POST(UPDATE_USER_LAST_SEEN)
    fun updateUserLastSeen(@FieldMap params: Map<String, String>): Call<BaseResponse>
}
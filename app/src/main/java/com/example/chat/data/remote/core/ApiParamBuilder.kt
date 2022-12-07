package com.example.chat.data.remote.core

class ApiParamBuilder {

    companion object {

        private const val PARAM_NAME = "name"
        private const val PARAM_EMAIL = "email"
        private const val PARAM_TOKEN = "token"
        private const val PARAM_IMAGE = "image"
        private const val PARAM_STATUS = "status"
        private const val PARAM_USER_ID = "user_id"
        private const val PARAM_MESSAGE = "message"
        private const val PARAM_PASSWORD = "password"
        private const val PARAM_USER_DATE = "user_date"
        private const val PARAM_OLD_TOKEN = "old_token"
        private const val PARAM_LAST_SEEN = "last_seen"
        private const val PARAM_SENDER_ID = "sender_id"
        private const val PARAM_FRIENDS_ID = "friends_id"
        private const val PARAM_CONTACT_ID = "contact_id"
        private const val PARAM_MESSAGE_ID = "message_id"
        private const val PARAM_RECEIVER_ID = "receiver_id"
        private const val PARAM_MESSAGE_DATE = "message_date"
        private const val PARAM_REQUEST_USER_ID = "request_user_id"
    }

    private var userId: Long? = null
    private var name: String? = null
    private var email: String? = null
    private var image: String? = null
    private var token: String? = null
    private var status: String? = null
    private var userDate: Long? = null
    private var lastSeen: Long? = null
    private var senderId: Long? = null
    private var friendsId: Long? = null
    private var messageId: Long? = null
    private var contactId: Long? = null
    private var message: String? = null
    private var oldToken: String? = null
    private var password: String? = null
    private var receiverId: Long? = null
    private var messageDate: Long? = null
    private var requestUserId: Long? = null

    fun userId(userId: Long): ApiParamBuilder {
        this.userId = userId
        return this
    }

    fun name(name: String): ApiParamBuilder {
        this.name = name
        return this
    }

    fun email(email: String): ApiParamBuilder {
        this.email = email
        return this
    }

    fun image(image: String): ApiParamBuilder {
        this.image = image
        return this
    }

    fun token(token: String): ApiParamBuilder {
        this.token = token
        return this
    }

    fun status(status: String): ApiParamBuilder {
        this.status = status
        return this
    }

    fun userDate(userDate: Long): ApiParamBuilder {
        this.userDate = userDate
        return this
    }

    fun lastSeen(lastSeen: Long): ApiParamBuilder {
        this.lastSeen = lastSeen
        return this
    }

    fun senderId(senderId: Long): ApiParamBuilder {
        this.senderId = senderId
        return this
    }

    fun friendsId(friendsId: Long): ApiParamBuilder {
        this.friendsId = friendsId
        return this
    }

    fun messageId(messageId: Long): ApiParamBuilder {
        this.messageId = messageId
        return this
    }

    fun contactId(contactId: Long): ApiParamBuilder {
        this.contactId = contactId
        return this
    }

    fun message(message: String): ApiParamBuilder {
        this.message = message
        return this
    }

    fun oldToken(oldToken: String): ApiParamBuilder {
        this.oldToken = oldToken
        return this
    }

    fun password(password: String): ApiParamBuilder {
        this.password = password
        return this
    }

    fun receiverId(receiverId: Long): ApiParamBuilder {
        this.receiverId = receiverId
        return this
    }

    fun messageDate(messageDate: Long): ApiParamBuilder {
        this.messageDate = messageDate
        return this
    }

    fun requestUserId(requestUserId: Long): ApiParamBuilder {
        this.requestUserId = requestUserId
        return this
    }

    fun build(): Map<String, String> {
        val params = HashMap<String, String>()
        name?.let { params[PARAM_NAME] = it }
        email?.let { params[PARAM_EMAIL] = it }
        image?.let { params[PARAM_IMAGE] = it }
        token?.let { params[PARAM_TOKEN] = it }
        status?.let { params[PARAM_STATUS] = it }
        message?.let { params[PARAM_MESSAGE] = it }
        password?.let { params[PARAM_PASSWORD] = it }
        oldToken?.let { params[PARAM_OLD_TOKEN] = it }
        userId?.let { params[PARAM_USER_ID] = it.toString() }
        userDate?.let { params[PARAM_USER_DATE] = it.toString() }
        lastSeen?.let { params[PARAM_LAST_SEEN] = it.toString() }
        senderId?.let { params[PARAM_SENDER_ID] = it.toString() }
        friendsId?.let { params[PARAM_FRIENDS_ID] = it.toString() }
        messageId?.let { params[PARAM_MESSAGE_ID] = it.toString() }
        contactId?.let { params[PARAM_CONTACT_ID] = it.toString() }
        receiverId?.let { params[PARAM_RECEIVER_ID] = it.toString() }
        messageDate?.let { params[PARAM_MESSAGE_DATE] = it.toString() }
        requestUserId?.let { params[PARAM_REQUEST_USER_ID] = it.toString() }
        return params
    }
}
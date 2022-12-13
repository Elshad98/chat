package com.example.chat.data.remote.common

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

    fun userId(userId: Long) = apply {
        this.userId = userId
    }

    fun name(name: String) = apply {
        this.name = name
    }

    fun email(email: String) = apply {
        this.email = email
    }

    fun image(image: String) = apply {
        this.image = image
    }

    fun token(token: String) = apply {
        this.token = token
    }

    fun status(status: String) = apply {
        this.status = status
    }

    fun userDate(userDate: Long) = apply {
        this.userDate = userDate
    }

    fun lastSeen(lastSeen: Long) = apply {
        this.lastSeen = lastSeen
    }

    fun senderId(senderId: Long) = apply {
        this.senderId = senderId
    }

    fun friendsId(friendsId: Long) = apply {
        this.friendsId = friendsId
    }

    fun messageId(messageId: Long) = apply {
        this.messageId = messageId
    }

    fun contactId(contactId: Long) = apply {
        this.contactId = contactId
    }

    fun message(message: String) = apply {
        this.message = message
    }

    fun oldToken(oldToken: String) = apply {
        this.oldToken = oldToken
    }

    fun password(password: String) = apply {
        this.password = password
    }

    fun receiverId(receiverId: Long) = apply {
        this.receiverId = receiverId
    }

    fun messageDate(messageDate: Long) = apply {
        this.messageDate = messageDate
    }

    fun requestUserId(requestUserId: Long) = apply {
        this.requestUserId = requestUserId
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
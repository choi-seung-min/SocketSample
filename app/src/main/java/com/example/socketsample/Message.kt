package com.example.socketsample

class Message {
    companion object{
        const val TYPE_MESSAGE = 0
        const val TYPE_LOG = 1
        const val TYPE_ACTION = 2
    }

    private var mType = 0
    private var mMessage: String? = null
    private var mUsername: String? = null

    private fun Message() {}

    fun getType(): Int {
        return mType
    }

    fun getMessage(): String? {
        return mMessage
    }

    fun getUsername(): String? {
        return mUsername
    }


    class Builder(private val mType: Int) {
        private var mUsername: String? = null
        private var mMessage: String? = null

        fun username(username: String?): Builder {
            mUsername = username
            return this
        }

        fun message(message: String?): Builder {
            mMessage = message
            return this
        }

        fun build(): Message {
            val message = Message()
            message.mType = mType
            message.mUsername = mUsername
            message.mMessage = mMessage
            return message
        }

    }
}
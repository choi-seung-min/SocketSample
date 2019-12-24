package com.example.socketsample

import android.app.Application
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import java.lang.RuntimeException
import java.net.URISyntaxException


class ChatApplication: Application(){
    private var mSocket: Socket? = null

    init {
        try {
            mSocket = IO.socket(Constants.CHAT_SERVER_URL)
        } catch (e: URISyntaxException){
            throw RuntimeException(e)
        }
    }

    fun getSocket(): Socket?{
        return mSocket
    }
}
package com.example.socketsample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.Socket
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject


class LoginActivity : Activity(){

    private lateinit var mSocket: Socket
    private lateinit var mUsername: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val app = application as ChatApplication
        mSocket = app.getSocket()!!

        username_input.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                if(p1 == 100 || p1 == EditorInfo.IME_NULL){
                    attemptLogin()
                    return true
                }
                return false
            }
        })

        sign_in_button.setOnClickListener {
            attemptLogin()
        }

        mSocket.on("login", onLogin)
    }

    override fun onDestroy() {
        super.onDestroy()
        mSocket.off("login", onLogin)
    }

    private fun attemptLogin(){
        username_input.error = null

        val userName = username_input.text.toString().trim()

        if (TextUtils.isEmpty(userName)){
            username_input.error = "set your nickname"
            username_input.requestFocus()
            return
        }
        mUsername = userName
        mSocket.emit("addUser", userName)
    }

    private val onLogin = Emitter.Listener { args ->
        val data = args[0] as JSONObject
        val numUsers: Int
        numUsers = try {
            data.getInt("numUsers")
        } catch (e: JSONException) {
            return@Listener
        }
        val intent = Intent()
        intent.putExtra("username", mUsername)
        intent.putExtra("numUsers", numUsers)
        setResult(RESULT_OK, intent)
        finish()
    }
}
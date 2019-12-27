package com.example.socketsample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MessageAdapter(context: Context, private var messages: List<Message>): RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    private var mUsernameColors: IntArray = context.resources.getIntArray(R.array.username_colors)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layout = -1
        when(viewType){
            Message.TYPE_MESSAGE -> layout = R.layout.item_message
            Message.TYPE_LOG -> layout = R.layout.item_log
            Message.TYPE_ACTION -> layout = R.layout.item_action
        }
        val v: View = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ViewHolder(v, mUsernameColors)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messages[position]
        holder.setMessage(message.getMessage()!!)
        holder.setUsername(message.getUsername()!!)
    }

    override fun getItemViewType(position: Int): Int {
        return messages[position].getType()
    }

    class ViewHolder(itemView: View, private val mUsernameColors : IntArray): RecyclerView.ViewHolder(itemView){
        private var mUsernameView: TextView = itemView.findViewById(R.id.username)
        private var mMessageView: TextView = itemView.findViewById(R.id.message)

        fun setUsername(username: String){
            mUsernameView.text = username
            mUsernameView.setTextColor(getUsernameColor(username))
        }

        fun setMessage(message: String){
            mMessageView.text = message
        }

        private fun getUsernameColor(username: String): Int{
            var hash = 7
            for(i in 0..username.length){
                hash = username.codePointAt(i) + (hash shl 5) - hash
            }
            val index = Math.abs(hash % mUsernameColors.size)
            return mUsernameColors[index]
        }
    }
}
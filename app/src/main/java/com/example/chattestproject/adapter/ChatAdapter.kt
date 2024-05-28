package com.example.chattestproject.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chattestproject.activity.ChatActivity

import com.example.chattestproject.model.UserModel
import com.example.chattestproject.R
import com.example.chattestproject.databinding.ChatUserItemLayoutBinding

class ChatAdapter(private val context: Context, private val list: ArrayList<UserModel>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: ChatUserItemLayoutBinding = ChatUserItemLayoutBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_user_item_layout, parent, false)
        return ChatViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    private fun capitalizeEveryWord(input: String): String {
        val words = input.split(" ")
        val result = StringBuilder()

        for (word in words) {
            result.append(word.capitalize()).append(" ")
        }

        return result.toString().trim() // Loại bỏ khoảng trắng ở cuối chuỗi
    }
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val user = list[position]
        holder.binding.userName.text = capitalizeEveryWord(user.fullname.toString())
//        holder.binding.userName.text = user.username

        // Use a placeholder image in case the imageUrl is null or empty


        holder.binding.userImage.setImageResource(R.drawable.user)


        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("uid",user.uid)
            intent.putExtra("username",user.username)
            context.startActivity(intent)
        }

    }
}

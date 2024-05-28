package com.example.chattestproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chattestproject.model.MessageModel
import com.example.chattestproject.R
import com.bumptech.glide.Glide
import com.example.chattestproject.databinding.ReceiverLayoutItemBinding
import com.example.chattestproject.databinding.ReceiverLayoutItemImgBinding
import com.example.chattestproject.databinding.SentItemLayoutBinding
import com.example.chattestproject.databinding.SentItemLayoutImgBinding
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(var context: Context, var list: ArrayList<MessageModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM_SENT = 1
    private val ITEM_RECEIVE = 2
    private val VIEW_TYPE_IMAGE_SENT = 3
    private val VIEW_TYPE_IMAGE_RECEIVED = 4

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_SENT -> SentViewHolder(
                LayoutInflater.from(context).inflate(R.layout.sent_item_layout, parent, false)
            )
            ITEM_RECEIVE -> ReceiverViewHolder(
                LayoutInflater.from(context).inflate(R.layout.receiver_layout_item, parent, false)
            )
            VIEW_TYPE_IMAGE_SENT -> SentImageViewHolder(
                LayoutInflater.from(context).inflate(R.layout.sent_item_layout_img, parent, false)
            )
            VIEW_TYPE_IMAGE_RECEIVED -> ReceiverImageViewHolder(
                LayoutInflater.from(context).inflate(R.layout.receiver_layout_item_img, parent, false)
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = list[position]
        return if (message.senderId == FirebaseAuth.getInstance().uid) {
            if (message.imageUrl != null) VIEW_TYPE_IMAGE_SENT else ITEM_SENT
        } else {
            if (message.imageUrl != null) VIEW_TYPE_IMAGE_RECEIVED else ITEM_RECEIVE
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = list[position]
        when (holder.itemViewType) {
            ITEM_SENT -> {
                val viewHolder = holder as SentViewHolder
                viewHolder.binding.userMsg.text = message.message
            }
            ITEM_RECEIVE -> {
                val viewHolder = holder as ReceiverViewHolder
                viewHolder.binding.userMsg.text = message.message
            }
            VIEW_TYPE_IMAGE_SENT -> {
                val viewHolder = holder as SentImageViewHolder
                Glide.with(context).load(message.imageUrl).into(viewHolder.binding.imageView)
            }
            VIEW_TYPE_IMAGE_RECEIVED -> {
                val viewHolder = holder as ReceiverImageViewHolder
                Glide.with(context).load(message.imageUrl).into(viewHolder.binding.imageView)
            }
        }
    }

    inner class SentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding: SentItemLayoutBinding = SentItemLayoutBinding.bind(view)
    }

    inner class ReceiverViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding: ReceiverLayoutItemBinding = ReceiverLayoutItemBinding.bind(view)
    }

    inner class SentImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding: SentItemLayoutImgBinding = SentItemLayoutImgBinding.bind(view)
    }

    inner class ReceiverImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding: ReceiverLayoutItemImgBinding = ReceiverLayoutItemImgBinding.bind(view)
    }
}

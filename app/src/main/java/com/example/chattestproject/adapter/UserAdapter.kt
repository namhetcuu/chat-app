//package com.example.chattestproject.adapter
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.chattestproject.R
//import com.example.chattestproject.model.UserModel
//import com.squareup.picasso.Picasso
//import de.hdodenhof.circleimageview.CircleImageView
//
//class UserAdapter(
//    mContext: Context,
//    mUsers: List<UserModel>,
//    isChatCheck: Boolean)
//    : RecyclerView.Adapter<UserAdapter.ViewHolder?>()
//{
//
//        private val mContext: Context
//        private val mUsers: List<UserModel>
//        private var isChatCheck: Boolean
//
//        init {
//            this.mUsers = mUsers
//            this.mContext = mContext
//            this.isChatCheck = isChatCheck
//        }
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
//        val view: View = LayoutInflater.from(mContext).inflate(R.layout.user_search_item_layout, viewGroup)
//        return UserAdapter.ViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return mUsers.size
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//
//        val user: UserModel? = mUsers[position]
//        holder.userNameTxt.text = user!!.username
//        Picasso.get().load(user.username).placeholder(R.drawable.user).into(holder.profileImageView)
//
//    }
//
//    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
//
//         var userNameTxt: TextView
//         var profileImageView: CircleImageView
//         var onlineImageView: CircleImageView
//         var offlineImageView: CircleImageView
//         var lastMassageTxt: TextView
//
//        init {
//            userNameTxt = itemView.findViewById(R.id.username)
//            profileImageView = itemView.findViewById(R.id.profile_image)
//            onlineImageView = itemView.findViewById(R.id.image_online)
//            offlineImageView = itemView.findViewById(R.id.image_offline)
//            lastMassageTxt = itemView.findViewById(R.id.message_last)
//
//        }
//
//    }
//
//
//
//
//
//}
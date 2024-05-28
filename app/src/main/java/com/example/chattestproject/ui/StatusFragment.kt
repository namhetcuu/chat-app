package com.example.chattestproject.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chattestproject.R

import com.example.chattestproject.databinding.ActivityMainBinding
import com.example.chattestproject.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class StatusFragment : Fragment() {

//    private var userAdapter: UserAdapter? = null
//    private var mUsers: List<UserModel>? =null
//    private var searchEditText: EditText? = null
//    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_status, container, false)


//        recyclerView = view.findViewById(R.id.searchList)
//        recyclerView!!.setHasFixedSize(true)
//        recyclerView!!.layoutManager = LinearLayoutManager(context)
//        searchEditText = view.findViewById(R.id.searchUsersET)
//
//        mUsers = ArrayList()
//        retrieveAllUsers()
//
//        searchEditText!!.addTextChangedListener(object : TextWatcher{
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(cs: CharSequence?, start: Int, before: Int, count: Int) {
//                searchForUser(cs.toString().toLowerCase())
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                TODO("Not yet implemented")
//            }
//
//        })

        return view
    }

//    private fun retrieveAllUsers()
//    {
//        val firebaseUserID = FirebaseAuth.getInstance().currentUser!!.uid
//
//        val refUsers = FirebaseDatabase.getInstance().reference.child("Users")
//
//        refUsers.addValueEventListener(object: ValueEventListener
//        {
//
//            override fun onDataChange(p0: DataSnapshot)
//            {
//
//                (mUsers as ArrayList<UserModel>).clear()
//                if(searchEditText!!.text.toString() == ""){
//                    for (snapshot in p0.children)
//                    {
//                        val user: UserModel? = snapshot.getValue(UserModel::class.java)
//                        if(!user!!.uid.equals(firebaseUserID))
//                        {
//                            (mUsers as ArrayList<UserModel>).add(user)
//                        }
//                    }
//                }
//
//
//                userAdapter = UserAdapter(context!!,mUsers!!,false)
//                recyclerView!!.adapter = userAdapter
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        })
//    }
//
//    private fun searchForUser(str: String){
//        val firebaseUserID = FirebaseAuth.getInstance().currentUser!!.uid
//        val queryUsers = FirebaseDatabase.getInstance().reference.child("Users")
//            .orderByChild("fullname").startAt(str).endAt(str + "\uf8ff")
//
//        queryUsers.addValueEventListener(object : ValueEventListener{
//            override fun onDataChange(p0: DataSnapshot) {
//                (mUsers as ArrayList<UserModel>).clear()
//
//                for (snapshot in p0.children)
//                {
//                    val user: UserModel? = snapshot.getValue(UserModel::class.java)
//                    if(!user!!.uid.equals(firebaseUserID))
//                    {
//                        (mUsers as ArrayList<UserModel>).add(user)
//                    }
//                }
//
//                userAdapter = UserAdapter(context!!,mUsers!!,false)
//                recyclerView!!.adapter = userAdapter
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
//    }
}
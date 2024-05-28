package com.example.chattestproject.activity

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.chattestproject.MainActivity
import com.example.chattestproject.adapter.MessageAdapter
import com.example.chattestproject.databinding.ActivityChatBinding
import com.example.chattestproject.model.MessageModel
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import java.util.*

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var storagePostPicRef: StorageReference

    private lateinit var senderUid: String
    private lateinit var receiverUid: String

    private lateinit var senderRoom: String
    private lateinit var receiverRoom: String

    private lateinit var list: ArrayList<MessageModel>
    private var imageUri: Uri? = null
    private var myUrl = ""

    companion object {
        const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        senderUid = FirebaseAuth.getInstance().uid.toString()
        receiverUid = intent.getStringExtra("uid")!!

        list = ArrayList()

        senderRoom = senderUid + receiverUid
        receiverRoom = receiverUid + senderUid

        database = FirebaseDatabase.getInstance()
        storagePostPicRef = FirebaseStorage.getInstance().reference.child("Chat Images")

        binding.userName.setText(intent.getStringExtra("username")!!)

        binding.imageView2.setOnClickListener {
            if (binding.messageBox.text.isEmpty()) {
                Toast.makeText(this, "Please enter your message", Toast.LENGTH_SHORT).show()
            } else {
                val message = MessageModel(
                    binding.messageBox.text.toString(),
                    senderUid,
                    Date().time.toString()
                )

                val randomKey = database.reference.push().key

                database.reference.child("chats")
                    .child(senderRoom).child("message").child(randomKey!!).setValue(message)
                    .addOnSuccessListener {
                        database.reference.child("chats").child(receiverRoom).child("message")
                            .child(randomKey!!).setValue(message).addOnSuccessListener {
                                binding.messageBox.text = null
                                Toast.makeText(this, "Message sent!!", Toast.LENGTH_SHORT).show()
                            }
                    }
            }
        }

        binding.chatBackBtn.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.imageButton.setOnClickListener {
            // Open gallery to select image
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
        }

        database.reference.child("chats").child(senderRoom).child("message")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    for (snapshot1 in snapshot.children) {
                        val data = snapshot1.getValue(MessageModel::class.java)
                        list.add(data!!)
                    }
                    binding.recyclerView.adapter = MessageAdapter(this@ChatActivity, list)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ChatActivity, "Error : $error", Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            uploadImage()
        }
    }

    private fun uploadImage() {
        when {
            imageUri == null -> Toast.makeText(this, "Please select image first", Toast.LENGTH_SHORT).show()
            else -> {
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Sending Image")
                progressDialog.setMessage("Please wait, we are uploading your image")
                progressDialog.show()

                val fileRef = storagePostPicRef.child(System.currentTimeMillis().toString() + ".jpg")
                var uploadTask: StorageTask<*>
                uploadTask = fileRef.putFile(imageUri!!)

                uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                            progressDialog.dismiss()
                        }
                    }
                    return@Continuation fileRef.downloadUrl
                }).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUrl = task.result
                        myUrl = downloadUrl.toString()

                        val message = MessageModel(
                            message = "Image",
                            senderId = senderUid,
                            timestamp = Date().time.toString(),
                            imageUrl = myUrl
                        )

                        val randomKey = database.reference.push().key

                        database.reference.child("chats")
                            .child(senderRoom).child("message").child(randomKey!!).setValue(message)
                            .addOnSuccessListener {
                                database.reference.child("chats").child(receiverRoom).child("message")
                                    .child(randomKey!!).setValue(message).addOnSuccessListener {
                                        Toast.makeText(this, "Image sent!!", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        progressDialog.dismiss()
                    } else {
                        progressDialog.dismiss()
                    }
                }
            }
        }
    }
}

package com.example.chattestproject.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.chattestproject.MainActivity
import com.example.chattestproject.databinding.ActivityProfileBinding
import com.example.chattestproject.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.Date

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private var selectedImg: Uri? = null
    private lateinit var dialog: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dialog = AlertDialog.Builder(this)
            .setMessage("Updating Profile...")
            .setCancelable(false)

        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()

        binding.userImage.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }

        binding.continueBtn.setOnClickListener {
            when {
                binding.userName.text!!.isEmpty() -> {
                    Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
                }
                selectedImg == null -> {
                    Toast.makeText(this, "Please select your image", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    uploadData()
                }
            }
        }
    }

    private fun uploadData() {
        val reference = storage.reference.child("Profile").child(Date().time.toString())
        selectedImg?.let {
            reference.putFile(it).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    reference.downloadUrl.addOnSuccessListener { uri ->
                        uploadInfo(uri.toString())
                    }
                }
            }
        }
    }

    private fun uploadInfo(imgUrl: String) {
        val user = UserModel(
            auth.uid.toString(),
            binding.userName.text.toString(),
            auth.currentUser!!.phoneNumber.toString(),
            imgUrl
        )

        database.reference.child("users")
            .child(auth.uid.toString())
            .setValue(user)
            .addOnSuccessListener {
                Toast.makeText(this, "Data inserted", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            selectedImg = data.data
            binding.userImage.setImageURI(selectedImg)
        }
    }
}

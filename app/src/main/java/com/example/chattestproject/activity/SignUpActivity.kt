package com.example.chattestproject.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.chattestproject.MainActivity
import com.example.chattestproject.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signinLinkBtn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        binding.signupBtn.setOnClickListener {
            createAccount()
        }
    }

    private fun createAccount() {
        val fullName = binding.fullnameSignup.text.toString()
        val username = binding.usernameSignup.text.toString()
        val email = binding.emailSignup.text.toString()
        val password = binding.passwordSignup.text.toString()

        when {
            TextUtils.isEmpty(fullName) -> Toast.makeText(
                this, "Please enter your full name", Toast.LENGTH_SHORT
            ).show()

            TextUtils.isEmpty(username) -> Toast.makeText(
                this, "Please enter your username", Toast.LENGTH_SHORT
            ).show()

            TextUtils.isEmpty(email) -> Toast.makeText(
                this, "Please enter your email", Toast.LENGTH_SHORT
            ).show()

            TextUtils.isEmpty(password) -> Toast.makeText(
                this, "Please enter your password", Toast.LENGTH_SHORT
            ).show()

            else -> {
                val progressDialog = ProgressDialog(this@SignUpActivity)
                progressDialog.setTitle("SignUp")
                progressDialog.setMessage("Please wait, this may take a while")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            saveUserInfo(fullName, username, email, progressDialog)
                        } else {
                            val message = task.exception!!.toString()
                            Toast.makeText(this, "Error : $message", Toast.LENGTH_SHORT).show()
                            mAuth.signOut()
                            progressDialog.dismiss()
                        }
                    }
            }
        }
    }

    private fun saveUserInfo(fullName: String, username: String, email: String, progressDialog: ProgressDialog) {
        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val usersRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")
        val userMap = HashMap<String, Any>()
        userMap["uid"] = currentUserID
        userMap["fullname"] = fullName.toLowerCase()
        userMap["username"] = username.toLowerCase()
        userMap["email"] = email.toLowerCase()
        userMap["image"] = "https://firebasestorage.googleapis.com/v0/b/chatapp-30147.appspot.com/o/Profile%2F1716395571416?alt=media&token=3afd1caf-3f73-41ec-982c-26632544a97c"
        usersRef.child(currentUserID).setValue(userMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    progressDialog.dismiss()
                    Toast.makeText(
                        this, "Account has been created successfully", Toast.LENGTH_SHORT
                    ).show()

                    FirebaseDatabase.getInstance().reference
                        .child("Follow").child(currentUserID)
                        .child("Following").child(currentUserID)
                        .setValue(true)

                    val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                } else {
                    val message = task.exception!!.toString()
                    Toast.makeText(this, "Error : $message", Toast.LENGTH_SHORT).show()
                    FirebaseAuth.getInstance().signOut()
                    progressDialog.dismiss()
                }
            }
    }
}
package com.example.chattestproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.example.chattestproject.activity.SignInActivity
import com.example.chattestproject.adapter.ViewPagerAdapter
import com.example.chattestproject.ui.ChatFragment

import com.example.chattestproject.databinding.ActivityMainBinding
import com.example.chattestproject.ui.SettingFragment
import com.example.chattestproject.ui.StatusFragment

import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private  var binding : ActivityMainBinding? = null
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val fragmentArrayList = ArrayList<Fragment>()

        fragmentArrayList.add(ChatFragment())
        fragmentArrayList.add(StatusFragment())
        fragmentArrayList.add(SettingFragment())

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser == null)
        {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }


        val adapter = ViewPagerAdapter(this, supportFragmentManager,fragmentArrayList)
        binding!!.viewPager.adapter = adapter
        binding!!.tabs.setupWithViewPager(binding!!.viewPager)

        binding!!.tlImage.setOnClickListener {
            Toast.makeText(applicationContext,"kkk",Toast.LENGTH_SHORT).show()


        }


    }
}
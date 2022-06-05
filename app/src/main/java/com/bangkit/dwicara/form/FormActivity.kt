package com.bangkit.dwicara.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.dwicara.databinding.ActivityFormBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FormActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityFormBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.btnLogout.setOnClickListener {
            Firebase.auth.signOut()
            finish()
        }
    }
}
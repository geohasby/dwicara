package com.bangkit.dwicara.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.dwicara.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}
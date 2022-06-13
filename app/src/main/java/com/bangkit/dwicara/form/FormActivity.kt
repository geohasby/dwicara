package com.bangkit.dwicara.form

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.dwicara.databinding.ActivityFormBinding

class FormActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityFormBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}
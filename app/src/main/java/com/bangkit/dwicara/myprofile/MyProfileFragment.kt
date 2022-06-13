package com.bangkit.dwicara.myprofile

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.dwicara.R
import com.bangkit.dwicara.databinding.FragmentLoginBinding
import com.bangkit.dwicara.databinding.FragmentMyProfileBinding
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MyProfileFragment : Fragment() {

    private var _binding: FragmentMyProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMyProfileBinding.inflate(LayoutInflater.from(container?.context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        setUpView()
    }

    private fun setUpView() {
        binding.btnBack.setOnClickListener {
            activity?.finish()
        }

        Glide.with(activity as Activity)
            .load("https://cdn.pixabay.com/photo/2017/11/02/14/27/model-2911332__340.jpg")
            .circleCrop()
            .into(binding.avatar)

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            activity?.finish()
        }

        Glide.with(this)
            .load("https://cdn.pixabay.com/photo/2012/04/10/23/01/indonesia-26817__480.png")
            .centerCrop()
            .into(binding.ivNative)

        Glide.with(this)
            .load("https://cdn.pixabay.com/photo/2012/04/10/16/14/union-jack-26119__480.png")
            .centerCrop()
            .into(binding.ivLearn)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
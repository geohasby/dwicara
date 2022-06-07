package com.bangkit.dwicara.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.dwicara.R
import com.bangkit.dwicara.article.ArticleActivity
import com.bangkit.dwicara.core.domain.User
import com.bangkit.dwicara.databinding.FragmentHomeBinding
import com.bangkit.dwicara.databinding.FragmentLoginBinding
import com.bumptech.glide.Glide

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val users = listOf<User>(
        User("Muhammad Faqih Wijaya", "https://cdn.pixabay.com/photo/2017/04/01/21/06/portrait-2194457__340.jpg"),
        User("Geohasby Ammar K", "https://cdn.pixabay.com/photo/2016/11/21/14/53/man-1845814__340.jpg"),
        User("Gilang Cey", "https://cdn.pixabay.com/photo/2017/11/02/14/27/model-2911332__340.jpg"),
        User("Nisrina Firdha Nabila","https://cdn.pixabay.com/photo/2015/03/03/18/58/woman-657753__340.jpg"),
        User("Erwin B P", "https://cdn.pixabay.com/photo/2016/11/21/14/53/man-1845814__340.jpg"),
        User("Akhyar Rasyidy","https://cdn.pixabay.com/photo/2015/07/20/12/57/ambassador-852766_960_720.jpg")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvHome.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapter = HomeItemAdapter(users)
        binding.rvHome.adapter = adapter

        binding.cvArticle.setOnClickListener{
            startActivity(Intent(activity, ArticleActivity::class.java))
        }

        Glide.with(this)
            .load("https://cdn.pixabay.com/photo/2018/05/08/13/56/globe-3383088__340.jpg")
            .fitCenter()
            .into(binding.ivPreview)
    }
}
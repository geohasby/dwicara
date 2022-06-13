package com.bangkit.dwicara.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.dwicara.article.ArticleActivity
import com.bangkit.dwicara.core.domain.Article
import com.bangkit.dwicara.core.domain.User
import com.bangkit.dwicara.databinding.FragmentHomeBinding
import com.bangkit.dwicara.recommendations.RecommendationsActivity
import com.bangkit.dwicara.search.SearchActivity
import com.bumptech.glide.Glide

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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

        setUpView()
    }

    private fun setUpView() {
        val adapter = BubbleItemAdapter(users)
        binding.rvHome.adapter = adapter

        binding.cvArticle.setOnClickListener{
            val intent = Intent(activity, ArticleActivity::class.java)
            intent.putExtra(EXTRA_ARTICLE, dailyArticle)
            startActivity(intent)
        }

        binding.btnSearch.setOnClickListener {
            startActivity(Intent(activity, SearchActivity::class.java))
        }

        binding.btnOthers.setOnClickListener {
            startActivity(Intent(activity, RecommendationsActivity::class.java))
        }

        binding.tvPreview.text = dailyArticle.title

        binding.tvContentPreview.text = dailyArticle.content

        Glide.with(this)
            .load("https://cdn.pixabay.com/photo/2018/05/08/13/56/globe-3383088__340.jpg")
            .centerCrop()
            .into(binding.ivPreview)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val EXTRA_ARTICLE = "extra_article"
        private val dailyArticle = Article(
            "https://cdn.pixabay.com/photo/2018/05/08/13/56/globe-3383088__340.jpg",
            "How to Pronounce Difficult English Word",
            "15 June 2022",
            "www.fluent.com",
            "General strategies are good to keep in mind, but the main challenge is always to pronounce individual words.\n" +
                    "\n" +
                    "This lesson presents seven words that are usually difficult for beginner learners. The instructor pronounces each one of them, focusing on the individual sounds and the stress.\n" +
                    "\n" +
                    "This material gives you a good idea how English spelling relates to common patterns of pronunciation. These patterns occur throughout the language and this video is a good first step towards building up your advanced vocabulary."
        )
        private val users = listOf(
            User(null, "Muhammad Faqih Wijaya", "https://cdn.pixabay.com/photo/2017/04/01/21/06/portrait-2194457__340.jpg",null,null,null),
            User(null, "Geohasby Ammar K", "https://cdn.pixabay.com/photo/2016/11/21/14/53/man-1845814__340.jpg",null,null,null),
            User(null, "Gilang Cey", "https://cdn.pixabay.com/photo/2017/11/02/14/27/model-2911332__340.jpg",null,null,null),
            User(null, "Nisrina Firdha Nabila","https://cdn.pixabay.com/photo/2015/03/03/18/58/woman-657753__340.jpg",null,null,null),
            User(null, "Erwin B P", "https://cdn.pixabay.com/photo/2016/11/21/14/53/man-1845814__340.jpg",null,null,null),
            User(null, "Akhyar Rasyidy","https://cdn.pixabay.com/photo/2015/07/20/12/57/ambassador-852766_960_720.jpg",null,null,null)
        )
    }
}
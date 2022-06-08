package com.bangkit.dwicara.recommendations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.dwicara.R
import com.bangkit.dwicara.core.domain.Interest
import com.bangkit.dwicara.core.domain.User
import com.bangkit.dwicara.databinding.ActivityRecommendationsBinding
import com.bangkit.dwicara.databinding.FilterBoxBinding

class RecommendationsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecommendationsBinding
    private lateinit var filterBinding: FilterBoxBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvChips.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = ChipsAdapter(interests)
        binding.rvChips.adapter = adapter

        binding.rvRecommendations.layoutManager = LinearLayoutManager(this)
        val adapterList = ListItemAdapter(users)
        binding.rvRecommendations.adapter = adapterList

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnFilter.setOnClickListener{
            createFilterDialog()
        }
    }

    private fun createFilterDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        filterBinding = FilterBoxBinding.inflate(layoutInflater)
        val filterPopUpView = filterBinding.root

        dialogBuilder.setView(filterPopUpView)
        dialogBuilder.create().show()
    }

    companion object {
        private val interests = listOf<Interest>(
            Interest("Android"),
            Interest("Movies"),
            Interest("Soccer"),
            Interest("Programming"),
            Interest("Foods")
        )
        private val users = listOf<User>(
            User("Muhammad Faqih Wijaya", "https://cdn.pixabay.com/photo/2017/04/01/21/06/portrait-2194457__340.jpg",null,null,null),
            User("Geohasby Ammar K", "https://cdn.pixabay.com/photo/2016/11/21/14/53/man-1845814__340.jpg",null,null,null),
            User("Gilang Cey", "https://cdn.pixabay.com/photo/2017/11/02/14/27/model-2911332__340.jpg",null,null,null),
            User("Nisrina Firdha Nabila","https://cdn.pixabay.com/photo/2015/03/03/18/58/woman-657753__340.jpg",null,null,null),
            User("Erwin B P", "https://cdn.pixabay.com/photo/2016/11/21/14/53/man-1845814__340.jpg",null,null,null),
            User("Akhyar Rasyidy","https://cdn.pixabay.com/photo/2015/07/20/12/57/ambassador-852766_960_720.jpg",null,null,null)
        )
    }
}
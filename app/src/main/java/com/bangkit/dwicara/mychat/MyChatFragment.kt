package com.bangkit.dwicara.mychat

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.dwicara.R
import com.bangkit.dwicara.core.domain.User
import com.bangkit.dwicara.databinding.FragmentHomeBinding
import com.bangkit.dwicara.databinding.FragmentMyChatBinding
import com.bangkit.dwicara.search.SearchActivity

class MyChatFragment : Fragment() {

    private var _binding: FragmentMyChatBinding? = null
    private val binding get() = _binding!!

    private val users = listOf<User>(
        User(null, "Muhammad Faqih Wijaya", "https://cdn.pixabay.com/photo/2017/04/01/21/06/portrait-2194457__340.jpg",null,null,null),
        User(null, "Geohasby Ammar K", "https://cdn.pixabay.com/photo/2016/11/21/14/53/man-1845814__340.jpg",null,null,null),
        User(null, "Gilang Cey", "https://cdn.pixabay.com/photo/2017/11/02/14/27/model-2911332__340.jpg",null,null,null),
        User(null, "Nisrina Firdha Nabila","https://cdn.pixabay.com/photo/2015/03/03/18/58/woman-657753__340.jpg",null,null,null),
        User(null, "Erwin B P", "https://cdn.pixabay.com/photo/2016/11/21/14/53/man-1845814__340.jpg",null,null,null),
        User(null, "Akhyar Rasyidy","https://cdn.pixabay.com/photo/2015/07/20/12/57/ambassador-852766_960_720.jpg",null,null,null)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyChatBinding.inflate(LayoutInflater.from(container?.context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvMychats.layoutManager = LinearLayoutManager(view.context)
        val chatUserAdapter = ChatUserAdapter(users)
        binding.rvMychats.adapter = chatUserAdapter

        binding.fabNew.setOnClickListener {
            startActivity(Intent(activity, SearchActivity::class.java))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
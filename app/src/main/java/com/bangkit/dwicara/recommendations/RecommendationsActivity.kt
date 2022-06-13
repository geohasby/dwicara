package com.bangkit.dwicara.recommendations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.dwicara.R
import com.bangkit.dwicara.core.domain.Interest
import com.bangkit.dwicara.core.domain.User
import com.bangkit.dwicara.databinding.ActivityRecommendationsBinding
import com.bangkit.dwicara.databinding.FilterBoxBinding

class RecommendationsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecommendationsBinding
    private lateinit var filterBoxBinding: FilterBoxBinding

    private lateinit var native: String
    private lateinit var learn: String

    private val selectedInterest = ArrayList<Interest>()

    private lateinit var interestChipsAdapter: ChipsAdapter
    private lateinit var interestRemovableChipsAdapter: ChipsAdapter

    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        interestChipsAdapter = ChipsAdapter(listOf())
        interestRemovableChipsAdapter = ChipsAdapter(selectedInterest, true) {
            removeSelectedInterest(it)
        }
        setUpView()
    }

    private fun removeSelectedInterest(interest: Interest) {
        val pos = selectedInterest.indexOf(interest)
        selectedInterest.remove(interest)
        interestRemovableChipsAdapter.notifyItemRemoved(pos)
    }

    private fun addSelectedInterest(interest: Interest) {
        if (selectedInterest.none { it.name == interest.name }) {
            selectedInterest.add(interest)
        }
        interestRemovableChipsAdapter.notifyItemInserted(interestRemovableChipsAdapter.itemCount)
    }

    private fun setUpView() {
        binding.rvChips.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvChips.adapter = interestChipsAdapter

        binding.rvRecommendations.layoutManager = LinearLayoutManager(this)
        val adapterList = ListItemAdapter(users)
        binding.rvRecommendations.adapter = adapterList

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnFilter.setOnClickListener {
            createFilterDialog()
        }
    }

    private fun createFilterDialog() {
        filterBoxBinding = FilterBoxBinding.inflate(layoutInflater)

        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setView(filterBoxBinding.root)
        dialog = dialogBuilder.create()
        dialog.show()

        setUpFilterBoxView()
    }

    private fun setUpFilterBoxView() {

        val nativeLanguageList = resources.getStringArray(R.array.native_language_list)
        val nativeSpinnerAdapter =
            object : ArrayAdapter<String>(this, R.layout.spinner_item, nativeLanguageList) {

                override fun isEnabled(position: Int): Boolean = (position != 0)

                override fun getDropDownView(
                    position: Int,
                    convertView: View?,
                    parent: ViewGroup
                ): View {
                    val view: TextView =
                        super.getDropDownView(position, convertView, parent) as TextView
                    if (position == 0) view.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.grey_500
                        )
                    )
                    else view.setTextColor(ContextCompat.getColor(context, R.color.grey_900))
                    return view
                }
            }
        nativeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        filterBoxBinding.etNative.adapter = nativeSpinnerAdapter
        filterBoxBinding.etNative.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                native = parent!!.getItemAtPosition(position).toString()
                if (native == nativeLanguageList[0])
                    (view as TextView).setTextColor(
                        ContextCompat.getColor(
                            this@RecommendationsActivity,
                            R.color.grey_500
                        )
                    )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val learnLanguageList = resources.getStringArray(R.array.learn_language_list)
        val learnSpinnerAdapter =
            object : ArrayAdapter<String>(this, R.layout.spinner_item, learnLanguageList) {

                override fun isEnabled(position: Int): Boolean = (position != 0)

                override fun getDropDownView(
                    position: Int,
                    convertView: View?,
                    parent: ViewGroup
                ): View {
                    val view: TextView =
                        super.getDropDownView(position, convertView, parent) as TextView
                    if (position == 0) view.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.grey_500
                        )
                    )
                    else view.setTextColor(ContextCompat.getColor(context, R.color.grey_900))
                    return view
                }
            }
        learnSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        filterBoxBinding.etLearn.adapter = learnSpinnerAdapter
        filterBoxBinding.etLearn.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                learn = parent!!.getItemAtPosition(position).toString()
                if (learn == learnLanguageList[0])
                    (view as TextView).setTextColor(
                        ContextCompat.getColor(
                            this@RecommendationsActivity,
                            R.color.grey_500
                        )
                    )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val autoTextViewAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            interests.map { it.name })
        filterBoxBinding.atvInterests.setAdapter(autoTextViewAdapter)

        filterBoxBinding.atvInterests.setOnClickListener {
            filterBoxBinding.atvInterests.showDropDown()
        }

        filterBoxBinding.atvInterests.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, pos, _ ->
                addSelectedInterest(Interest(autoTextViewAdapter.getItem(pos) as String))
                filterBoxBinding.atvInterests.text.clear()
            }

        filterBoxBinding.rvInterest.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        filterBoxBinding.rvInterest.adapter = interestRemovableChipsAdapter

        filterBoxBinding.btnApply.setOnClickListener {
            applyFilter()
            dialog.cancel()
        }
    }

    private fun applyFilter() {
        interestChipsAdapter = ChipsAdapter(selectedInterest)
        binding.rvChips.adapter = interestChipsAdapter
    }

    companion object {
        val interests = listOf(
            Interest("Android"),
            Interest("Movies"),
            Interest("Soccer"),
            Interest("Programming"),
            Interest("Foods"),
            Interest("Anime"),
            Interest("Health"),
            Interest("Games"),
        )
        private val users = listOf(
            User(
                null,
                "Muhammad Faqih Wijaya",
                "https://cdn.pixabay.com/photo/2017/04/01/21/06/portrait-2194457__340.jpg",
                null,
                null,
                null
            ),
            User(
                null,
                "Geohasby Ammar K",
                "https://cdn.pixabay.com/photo/2016/11/21/14/53/man-1845814__340.jpg",
                null,
                null,
                null
            ),
            User(
                null,
                "Gilang Cey",
                "https://cdn.pixabay.com/photo/2017/11/02/14/27/model-2911332__340.jpg",
                null,
                null,
                null
            ),
            User(
                null,
                "Nisrina Firdha Nabila",
                "https://cdn.pixabay.com/photo/2015/03/03/18/58/woman-657753__340.jpg",
                null,
                null,
                null
            ),
            User(
                null,
                "Erwin B P",
                "https://cdn.pixabay.com/photo/2016/11/21/14/53/man-1845814__340.jpg",
                null,
                null,
                null
            ),
            User(
                null,
                "Akhyar Rasyidy",
                "https://cdn.pixabay.com/photo/2015/07/20/12/57/ambassador-852766_960_720.jpg",
                null,
                null,
                null
            )
        )
    }
}
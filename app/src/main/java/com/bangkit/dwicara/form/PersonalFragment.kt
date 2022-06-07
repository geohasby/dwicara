package com.bangkit.dwicara.form

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bangkit.dwicara.R
import com.bangkit.dwicara.databinding.FragmentPersonalBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class PersonalFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentPersonalBinding? = null
    private val binding get() = _binding!!

    private lateinit var username: String
    private lateinit var fullname: String
    private lateinit var birthday: String
    private lateinit var gender: String

    private val cal: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPersonalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogout.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)

        val date = DatePickerDialog.OnDateSetListener{ _, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()
        }

        binding.etBirthday.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                date,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        val genderList = resources.getStringArray(R.array.gender_list)
//        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genderList)
        val spinnerAdapter = object : ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, genderList) {

            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
                if(position == 0) {
                    view.setTextColor(getColor(context, R.color.grey_500))
                } else {
                    view.setTextColor(getColor(context, R.color.grey_900))
                }
                return view
            }
        }

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.etGender.adapter = spinnerAdapter

        binding.etGender.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                gender = parent!!.getItemAtPosition(position).toString()
                if(gender == genderList[0]){
                    (view as TextView).setTextColor(getColor(requireContext(), R.color.grey_500))
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun updateLabel() {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.etBirthday.text = sdf.format(cal.time)
    }

    private fun validate(): Boolean {
        username = binding.etUsername.text.toString()
        fullname = binding.etFullname.text.toString()
        birthday = binding.etBirthday.text.toString()

        // Empty Check
        if (username.isEmpty()) {
            binding.etUsername.error = getString(R.string.field_empty_error, "username")
            binding.etUsername.requestFocus()
            return false
        }

        if (fullname.isEmpty()) {
            binding.etFullname.error = getString(R.string.field_empty_error, "full name")
            binding.etFullname.requestFocus()
            return false
        }

        if (birthday.isEmpty()) {
            binding.etBirthday.error = getString(R.string.field_empty_error, "birthday")
            binding.etBirthday.requestFocus()
            return false
        }

        if(!(gender == "Male" || gender == "Female")) {
            val errorText = binding.etGender.selectedView as TextView
            errorText.error = getString(R.string.field_empty_error, "gender")
//            binding.etGender.error = getString(R.string.field_empty_error, "birthday")
            binding.etGender.requestFocus()
            return false
        }

        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_logout -> {
                Firebase.auth.signOut()
                activity?.finish()
            }
            R.id.btn_next -> {
                if(validate()){
                    findNavController().navigate(R.id.action_personalFragment_to_languageFragment)
                }
            }
        }
    }
}
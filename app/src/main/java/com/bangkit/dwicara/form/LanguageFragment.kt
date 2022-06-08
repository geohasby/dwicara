package com.bangkit.dwicara.form

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bangkit.dwicara.MainActivity
import com.bangkit.dwicara.R
import com.bangkit.dwicara.databinding.FragmentLanguageBinding

class LanguageFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentLanguageBinding? = null
    private val binding get() = _binding!!

    private lateinit var native: String
    private lateinit var learn: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLanguageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener(this)
        binding.btnSave.setOnClickListener(this)

        val nativeLanguageList = resources.getStringArray(R.array.native_language_list)
        val nativeSpinnerAdapter = object : ArrayAdapter<String>(requireContext(), R.layout.spinner_item, nativeLanguageList) {

            override fun isEnabled(position: Int): Boolean = (position != 0)

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
                if(position == 0) view.setTextColor(ContextCompat.getColor(context, R.color.grey_500))
                else view.setTextColor(ContextCompat.getColor(context, R.color.grey_900))
                return view
            }
        }

        nativeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.etNative.adapter = nativeSpinnerAdapter

        binding.etNative.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                native = parent!!.getItemAtPosition(position).toString()
                if(native == nativeLanguageList[0])
                    (view as TextView).setTextColor(ContextCompat.getColor(requireContext(), R.color.grey_500))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val learnLanguageList = resources.getStringArray(R.array.learn_language_list)
        val learnSpinnerAdapter = object : ArrayAdapter<String>(requireContext(), R.layout.spinner_item, learnLanguageList) {

            override fun isEnabled(position: Int): Boolean = (position != 0)

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
                if(position == 0) view.setTextColor(ContextCompat.getColor(context, R.color.grey_500))
                else view.setTextColor(ContextCompat.getColor(context, R.color.grey_900))
                return view
            }
        }

        learnSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.etLearn.adapter = learnSpinnerAdapter

        binding.etLearn.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                learn = parent!!.getItemAtPosition(position).toString()
                if(learn == learnLanguageList[0])
                    (view as TextView).setTextColor(ContextCompat.getColor(requireContext(), R.color.grey_500))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun validate(): Boolean {

        if(!(native == "English" || native == "Indonesia")) {
            val errorText = binding.etNative.selectedView as TextView
            errorText.error = getString(R.string.field_empty_error, "native language")
            binding.etNative.requestFocus()
            return false
        }

        if(!(learn == "English" || learn == "Indonesia")) {
            val errorText = binding.etLearn.selectedView as TextView
            errorText.error = getString(R.string.field_empty_error, "learn language")
            binding.etLearn.requestFocus()
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
            R.id.btn_back -> {
                findNavController().navigate(R.id.action_languageFragment_to_personalFragment)
            }
            R.id.btn_save -> {
                if(validate()){
                    startActivity(Intent(activity, MainActivity::class.java))
                }
            }
        }
    }
}
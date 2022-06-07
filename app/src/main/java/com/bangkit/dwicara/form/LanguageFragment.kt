package com.bangkit.dwicara.form

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

        val languageList = resources.getStringArray(R.array.language_list)
        val spinnerAdapter = object : ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, languageList) {

            override fun isEnabled(position: Int): Boolean = (position != 0)

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
                if(position == 0) view.setTextColor(ContextCompat.getColor(context, R.color.grey_500))
                else view.setTextColor(ContextCompat.getColor(context, R.color.grey_900))
                return view
            }
        }

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.etNative.adapter = spinnerAdapter
        binding.etLearn.adapter = spinnerAdapter

        binding.etNative.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                native = parent!!.getItemAtPosition(position).toString()
                if(native == languageList[0])
                    (view as TextView).setTextColor(ContextCompat.getColor(requireContext(), R.color.grey_500))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.etLearn.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                learn = parent!!.getItemAtPosition(position).toString()
                if(learn == languageList[0])
                    (view as TextView).setTextColor(ContextCompat.getColor(requireContext(), R.color.grey_500))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_back -> {
                findNavController().navigate(R.id.action_languageFragment_to_personalFragment)}
            R.id.btn_save -> {}
        }
    }
}
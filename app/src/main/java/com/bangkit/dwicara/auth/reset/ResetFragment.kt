package com.bangkit.dwicara.auth.reset

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.bangkit.dwicara.R
import com.bangkit.dwicara.databinding.FragmentLoginBinding
import com.bangkit.dwicara.databinding.FragmentResetBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ResetFragment : Fragment() {

    private var _binding: FragmentResetBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentResetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        binding.etEmail.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // do nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // do nothing
            }

            override fun afterTextChanged(txt: Editable?) {
                if(txt.toString().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(txt.toString()).matches()) {
                    binding.etEmail.setError(context?.getString(R.string.invalid_email_error), null)
                } else {
                    binding.etEmail.error = null
                }
            }
        })

        binding.btnBack.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_resetFragment_to_loginFragment)
        }

        binding.btnReset.setOnClickListener {
            resetPassword()
        }
    }

    private fun resetPassword() {
        if(isValidForm()) {
            val email = binding.etEmail.text.toString().trim()
            firebaseSendResetPasswordLink(email)
        }
    }

    private fun firebaseSendResetPasswordLink(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    Log.d(TAG_RESET, "Email sent.")
                    Toast.makeText(context, "Email sent, please check your inbox or spam", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d(TAG_RESET, "Failed to send email.")
                    Toast.makeText(context, "email reset failed : ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
                updateUI(task.isSuccessful)
            }
    }

    private fun isValidForm(): Boolean {
        val email = binding.etEmail.text.toString().trim()
        var isValid = true

        when {
            email.isEmpty() -> {
                binding.etEmail.error = context?.getString(R.string.field_empty_error, "email")
                binding.etEmail.requestFocus()
                isValid = false
            }
            binding.etEmail.error != null -> {
                binding.etEmail.requestFocus()
                isValid = false
            }
        }

        return isValid
    }

    private fun updateUI(isSuccess: Boolean) {
        if(isSuccess) {
            Navigation.findNavController(binding.btnReset).navigate(R.id.action_resetFragment_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG_RESET = "RESET PASSWORD"
    }
}
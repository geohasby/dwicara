package com.bangkit.dwicara.auth.login

import android.app.Activity
import android.content.Intent
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
import com.bangkit.dwicara.form.FormActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        binding.btnToRegister.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener {
            signInWithEmail()
        }

        binding.etEmail.addTextChangedListener(object: TextWatcher{
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

        binding.btnForget.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_resetFragment)
        }
    }

    private fun isValidForm(): Boolean {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        var isValid = true

        when {
            email.isEmpty() -> {
                binding.etEmail.error = "Please fill out the email field!"
                binding.etEmail.requestFocus()
                isValid = false
            }
            // check for other errors like wrong email format
            binding.etEmail.error != null -> {
                binding.etEmail.requestFocus()
                isValid = false
            }
            password.isEmpty() -> {
                binding.etPassword.error = "Please fill out the password field!"
                binding.etPassword.requestFocus()
                isValid = false
            }
            // check for other errors like wrong password format
            //password at least 6  character long
            binding.etPassword.error != null -> {
                binding.etPassword.requestFocus()
                isValid = false
            }
        }
        return isValid
    }

    private fun signInWithEmail() {
        if(isValidForm()) {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            firebaseAuthWithEmail(email, password)
        }
    }

    private fun firebaseAuthWithEmail(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity as Activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG_EMAIL, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG_EMAIL, "signInWithEmail:failure", task.exception)
                    Toast.makeText(context, "login failed : ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            startActivity(Intent(activity, FormActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    companion object {
        private const val TAG_EMAIL = "EMAIL SIGN IN"
    }
}
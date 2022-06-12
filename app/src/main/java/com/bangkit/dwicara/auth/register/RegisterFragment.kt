package com.bangkit.dwicara.auth.register

import android.app.Activity
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
import com.bangkit.dwicara.core.domain.User
import com.bangkit.dwicara.databinding.FragmentRegisterBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.HashMap

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_registerFragment_to_loginFragment)
        }

        auth = Firebase.auth

        binding.btnRegister.setOnClickListener {
            signUp()
        }

        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // do nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // do nothing
            }

            override fun afterTextChanged(txt: Editable?) {
                if (txt.toString().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(txt.toString())
                        .matches()
                ) {
                    binding.etEmail.setError(context?.getString(R.string.invalid_email_error), null)
                } else {
                    binding.etEmail.error = null
                }
            }
        })

        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // do nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // do nothing)
            }

            override fun afterTextChanged(txt: Editable?) {
                if (txt.toString().length < PWD_MIN_LENGTH) {
                    binding.etPassword.setError(
                        context?.getString(R.string.invalid_password_format),
                        null
                    )
                } else {
                    binding.etPassword.error = null
                }
            }
        })

        binding.etConfirm.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // do nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // do nothing
            }

            override fun afterTextChanged(txt: Editable?) {
                val pwd = binding.etPassword.text.toString().trim()
                val pwdConfirm = binding.etConfirm.text.toString().trim()
                if (pwd != pwdConfirm) {
                    binding.etConfirm.error = context?.getString(R.string.password_doesnt_match)
                } else {
                    binding.etConfirm.error = null
                }
            }

        })
    }

    private fun isValidForm(): Boolean {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val passwordConfirm = binding.etConfirm.text.toString().trim()
        var isValid = true

        when {
            email.isEmpty() -> {
                binding.etEmail.error = getString(R.string.field_empty_error, "email")
                binding.etEmail.requestFocus()
                isValid = false
            }
            // check for other errors like wrong email format
            binding.etEmail.error != null -> {
                binding.etEmail.requestFocus()
                isValid = false
            }
            password.isEmpty() -> {
                binding.etPassword.error = getString(R.string.field_empty_error, "password")
                binding.etPassword.requestFocus()
                isValid = false
            }
            // check for other errors like wrong password format
            //password at least 6  character long
            binding.etPassword.error != null -> {
                binding.etPassword.requestFocus()
                isValid = false
            }
            passwordConfirm.isEmpty() -> {
                binding.etConfirm.error = getString(R.string.field_empty_error, "confirmation")
                binding.etConfirm.requestFocus()
                isValid = false
            }
            // check for other errors like password confirmation doesn't match
            binding.etConfirm.error != null -> {
                binding.etConfirm.requestFocus()
                isValid = false
            }
        }
        return isValid
    }

    private fun signUp() {
        if (isValidForm()) {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            firebaseCreateUserWithEmailPassword(email, password)
        }
    }

    private fun firebaseCreateUserWithEmailPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity as Activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG_REGISTER, "createUserWithEmail:success")
                    val user = auth.currentUser
                    Log.d("USER", "${user?.displayName} ${user?.email} ${user?.uid}")
                    addNewUserToDatabase(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG_REGISTER, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        context,
                        "register failed : ${task.exception?.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun addNewUserToDatabase(user: FirebaseUser?) {
        val id = user?.uid ?: ""
        val name = user?.displayName ?: ""
        val email = user?.email ?: ""
        val photo_url = user?.photoUrl.toString()
        val reference = FirebaseFirestore.getInstance().collection("users")
        val newData = HashMap<String, Any>()
        newData.put("id", id)
        newData.put("name", name)
        newData.put("email", email)
        newData.put("photo_url", photo_url)

        reference.add(newData).addOnCompleteListener(activity as Activity) { task ->
            if(task.isSuccessful) {
                updateUI(user)
            } else {
                Log.w(TAG_REGISTER, "createUserInDatabase:failure", task.exception)
                Toast.makeText(
                    context,
                    "register failed : ${task.exception?.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            Log.d(TAG_REGISTER, "update UI works")
            Navigation.findNavController(binding.btnRegister)
                .navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG_REGISTER = "REGISTER"
        private const val PWD_MIN_LENGTH = 6
    }
}
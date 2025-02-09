package com.app.homework_number_six.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.app.homework_number_six.R
import com.app.homework_number_six.databinding.FragmentLoginBinding
import com.app.homework_number_six.di.ServiceLocator
import com.app.homework_number_six.utils.AuthPreferences
import com.app.homework_number_six.utils.ValidationUtils.isValidEmail
import com.app.homework_number_six.utils.ValidationUtils.isValidEmailOrNickname
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var userRepository = ServiceLocator.getUserRepository()
    private var binding: FragmentLoginBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        binding?.run {
            buttonLogin.setOnClickListener {
                val input = editTextEmail.text.toString().trim()
                val password = editTextPassword.text.toString().trim()

                resetErrorFields()

                if (input.isEmpty()) {
                    textInputLayoutEmail.error = getString(R.string.please_enter_your_email_address)
                    return@setOnClickListener
                }
                if (password.isEmpty()) {
                    textInputLayoutPassword.error = getString(R.string.please_enter_the_password)
                    return@setOnClickListener
                }

                if (!isValidEmailOrNickname(input)) {
                    textInputLayoutEmail.error =
                        getString(R.string.the_email_address_or_nickname_was_entered_incorrectly)
                    return@setOnClickListener
                }

                lifecycleScope.launch {
                    val user = if (isValidEmail(input)) {
                        userRepository.getUserByEmail(input)
                    } else {
                        userRepository.getUserByNickname(input)
                    }

                    if (user != null && user.password == password) {
                        withContext(Dispatchers.Main) {
                            AuthPreferences.saveUserId(requireContext(), user.id)

                            Toast.makeText(requireContext(),
                                getString(R.string.authorization_was_successful), Toast.LENGTH_SHORT).show()

                            parentFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, FilmsFragment(),
                                    getString(R.string.films))
                                .addToBackStack(null)
                                .commit()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(requireContext(),
                                getString(R.string.this_user_is_not_registered_or_the_password_is_incorrect), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            buttonRegister.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, RegisterFragment(), getString(R.string.register))
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun resetErrorFields() {
        binding?.run {
            textInputLayoutEmail.error = null
            textInputLayoutEmail.isErrorEnabled = false
            textInputLayoutPassword.error = null
            textInputLayoutPassword.isErrorEnabled = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
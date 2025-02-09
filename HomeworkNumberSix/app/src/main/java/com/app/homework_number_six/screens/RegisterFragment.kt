package com.app.homework_number_six.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.app.homework_number_six.R
import com.app.homework_number_six.db.entities.UserEntity
import com.app.homework_number_six.databinding.FragmentRegisterBinding
import com.app.homework_number_six.di.ServiceLocator
import com.app.homework_number_six.utils.AuthPreferences
import com.app.homework_number_six.utils.ValidationUtils.isValidEmail
import com.app.homework_number_six.utils.ValidationUtils.isValidName
import com.app.homework_number_six.utils.ValidationUtils.isValidNickname
import com.app.homework_number_six.utils.ValidationUtils.isValidPassword

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private var userRepository = ServiceLocator.getUserRepository()
    private var binding: FragmentRegisterBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        binding?.run {
            buttonRegister.setOnClickListener {
                val email = editTextEmail.text.toString().trim()
                val nickname = editTextNickname.text.toString().trim()
                val firstName = editTextFirstName.text.toString().trim()
                val lastName = editTextLastName.text.toString().trim()
                val password = editTextPassword.text.toString().trim()
                val confirmPassword = editTextConfirmPassword.text.toString().trim()

                resetErrorFields()

                if (email.isEmpty()) {
                    textInputLayoutEmail.error = getString(R.string.please_enter_your_email_address)
                }
                if (nickname.isEmpty()) {
                    textInputLayoutNickname.error = getString(R.string.please_enter_your_nickname)
                }
                if (firstName.isEmpty()) {
                    textInputLayoutFirstName.error = getString(R.string.please_enter_your_name)
                }
                if (lastName.isEmpty()) {
                    textInputLayoutLastName.error = getString(R.string.please_enter_your_last_name)
                }
                if (password.isEmpty()) {
                    textInputLayoutPassword.error = getString(R.string.please_enter_the_password)
                }
                if (confirmPassword.isEmpty()) {
                    textInputLayoutConfirmPassword.error =
                        getString(R.string.please_enter_the_password_again)
                }

                if (textInputLayoutEmail.error != null || textInputLayoutNickname.error != null ||
                    textInputLayoutFirstName.error != null || textInputLayoutLastName.error != null ||
                    textInputLayoutPassword.error != null || textInputLayoutConfirmPassword.error != null) {
                    return@setOnClickListener
                }

                if (!isValidEmail(email)) {
                    textInputLayoutEmail.error =
                        getString(R.string.an_incorrectly_entered_email_address)
                    return@setOnClickListener
                }

                if (!isValidNickname(nickname)) {
                    textInputLayoutNickname.error =
                        getString(R.string.the_nickname_should_contain_only_letters_and_numbers_3_20_characters)
                    return@setOnClickListener
                }

                if (!isValidName(firstName)) {
                    textInputLayoutFirstName.error =
                        getString(R.string.the_name_must_contain_only_letters_2_50_characters)
                    return@setOnClickListener
                }

                if (!isValidName(lastName)) {
                    textInputLayoutLastName.error =
                        getString(R.string.the_last_name_must_contain_only_letters_2_50_characters)
                    return@setOnClickListener
                }

                if (!isValidPassword(password)) {
                    textInputLayoutPassword.error =
                        getString(R.string.the_password_must_contain_at_least_6_characters_including_a_capital_letter_and_a_number)
                    return@setOnClickListener
                }

                if (password != confirmPassword) {
                    textInputLayoutConfirmPassword.error =
                        getString(R.string.the_passwords_don_t_match)
                    return@setOnClickListener
                }

                lifecycleScope.launch {
                    val existingUserByEmail = userRepository.getUserByEmail(email)
                    val existingUserByNickname = userRepository.getUserByNickname(nickname)

                    if (existingUserByEmail != null) {
                        withContext(Dispatchers.Main) {
                            textInputLayoutEmail.error =
                                getString(R.string.the_user_with_this_email_address_is_already_registered)
                        }
                        return@launch
                    }

                    if (existingUserByNickname != null) {
                        withContext(Dispatchers.Main) {
                            textInputLayoutNickname.error =
                                getString(R.string.the_user_with_this_nickname_has_already_been_registered)
                        }
                        return@launch
                    }

                    val user = UserEntity(
                        id = UUID.randomUUID().toString(),
                        email = email,
                        nickname = nickname,
                        firstName = firstName,
                        lastName = lastName,
                        password = password,
                        confirmPassword = confirmPassword
                    )

                    userRepository.saveUser(user)

                    withContext(Dispatchers.Main) {
                        AuthPreferences.saveUserId(requireContext(), user.id)

                        Toast.makeText(requireContext(),
                            getString(R.string.registration_was_successful), Toast.LENGTH_SHORT).show()

                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, FilmsFragment(), getString(R.string.films))
                            .addToBackStack(null)
                            .commit()
                    }
                }
            }
        }
    }

    private fun resetErrorFields() {
        binding?.run {
            textInputLayoutEmail.error = null
            textInputLayoutEmail.isErrorEnabled = false
            textInputLayoutNickname.error = null
            textInputLayoutNickname.isErrorEnabled = false
            textInputLayoutFirstName.error = null
            textInputLayoutFirstName.isErrorEnabled = false
            textInputLayoutLastName.error = null
            textInputLayoutLastName.isErrorEnabled = false
            textInputLayoutPassword.error = null
            textInputLayoutPassword.isErrorEnabled = false
            textInputLayoutConfirmPassword.error = null
            textInputLayoutConfirmPassword.isErrorEnabled = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
package com.app.homework_number_six.screens

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.app.homework_number_six.R
import com.app.homework_number_six.databinding.FragmentSettingsBinding
import com.app.homework_number_six.di.ServiceLocator
import com.app.homework_number_six.utils.AuthPreferences
import com.app.homework_number_six.utils.ValidationUtils
import com.app.homework_number_six.utils.logout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var binding: FragmentSettingsBinding? = null
    private val userRepository = ServiceLocator.getUserRepository()
    private var userId: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)

        userId = AuthPreferences.getUserId(requireContext()) ?: ""

        lifecycleScope.launch {
            val user = userRepository.getUserById(userId!!)
            user?.let { currentUser ->
                with(binding!!) {
                    editTextNickname.setText(currentUser.nickname)
                    editTextEmail.setText(currentUser.email)
                }
            }
        }

        binding?.buttonSaveNickname?.setOnClickListener {
            saveNickname()
        }

        binding?.buttonSaveEmail?.setOnClickListener {
            saveEmail()
        }

        binding?.buttonSavePassword?.setOnClickListener {
            savePassword()
        }

        binding?.buttonDeleteAccount?.setOnClickListener {
            deleteUserAccount()
        }
    }

    private fun saveNickname() {
        val newNickname = binding?.editTextNickname?.text.toString().trim()

        if (newNickname.isEmpty()) {
            Toast.makeText(requireContext(),
                getString(R.string.nickname_cannot_be_empty), Toast.LENGTH_SHORT).show()
            return
        }

        if (!ValidationUtils.isValidNickname(newNickname)) {
            Toast.makeText(
                requireContext(),
                getString(R.string.the_nickname_must_contain_only_letters_and_numbers_3_20_characters),
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        lifecycleScope.launch {
            val existingUser = userRepository.getUserByNickname(newNickname)
            if (existingUser != null) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.this_nickname_is_already_in_use),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return@launch
            }

            val updatedUser = (userRepository.getUserById(userId!!))?.copy(nickname = newNickname)
            updatedUser?.let {
                userRepository.saveUser(it)
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(),
                        getString(R.string.nickname_successfully_updated), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun saveEmail() {
        val newEmail = binding?.editTextEmail?.text.toString().trim()

        if (newEmail.isEmpty()) {
            Toast.makeText(requireContext(),
                getString(R.string.email_cannot_be_empty), Toast.LENGTH_SHORT).show()
            return
        }

        if (!ValidationUtils.isValidEmail(newEmail)) {
            Toast.makeText(
                requireContext(),
                getString(R.string.please_enter_a_valid_email),
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        lifecycleScope.launch {
            val existingUser = userRepository.getUserByEmail(newEmail)
            if (existingUser != null) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.this_email_is_already_registered),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return@launch
            }

            val updatedUser = (userRepository.getUserById(userId!!))?.copy(email = newEmail)
            updatedUser?.let {
                userRepository.saveUser(it)
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(),
                        getString(R.string.email_updated_successfully), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun savePassword() {
        val currentPassword = binding?.editTextCurrentPassword?.text.toString().trim()
        val newPassword = binding?.editTextNewPassword?.text.toString().trim()
        val confirmPassword = binding?.editTextConfirmPassword?.text.toString().trim()

        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(requireContext(), getString(R.string.fill_in_all_fields), Toast.LENGTH_SHORT).show()
            return
        }

        if (newPassword != confirmPassword) {
            Toast.makeText(requireContext(), getString(R.string.the_passwords_do_not_match), Toast.LENGTH_SHORT).show()
            return
        }

        if (!ValidationUtils.isValidPassword(newPassword)) {
            Toast.makeText(
                requireContext(),
                getString(R.string.the_new_password_must_contain_at_least_6_characters_including_a_capital_letter_and_a_number),
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        lifecycleScope.launch {
            val user = userRepository.getUserById(userId!!)
            if (user == null || user.password != currentPassword) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.the_current_password_is_incorrect),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return@launch
            }

            val updatedUser = user.copy(password = newPassword, confirmPassword = newPassword)
            userRepository.saveUser(updatedUser)
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(),
                    getString(R.string.password_updated_successfully), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteUserAccount() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.confirm_the_action))
            .setMessage(getString(R.string.are_you_sure_you_want_to_delete_your_account))
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                lifecycleScope.launch {
                    userRepository.deleteUserById(userId!!)
                    logout(requireContext())
                    Toast.makeText(requireContext(), getString(R.string.account_deleted), Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .create()
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        userId = null
    }
}
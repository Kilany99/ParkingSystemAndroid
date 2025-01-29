package com.example.parkingsystemandroid.ui.auth.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.parkingsystemandroid.R
import com.example.parkingsystemandroid.databinding.FragmentForgotPasswordBinding
import com.example.parkingsystemandroid.databinding.FragmentResetPasswordBinding
import com.example.parkingsystemandroid.viewmodel.AuthViewModel
import com.example.parkingsystemandroid.viewmodel.ResponseState


class ResetPasswordFragment : Fragment() {

    private lateinit var viewModel: AuthViewModel

    private var _binding: FragmentResetPasswordBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)


        binding.buttonResetPassword.setOnClickListener {
            val resetToken = binding.editTextResetToken.text.toString().trim()
            val newPassword = binding.editTextNewPassword.text.toString()
            val confirmPassword = binding.editTextConfirmPassword.text.toString()
            //validation
            if (validateInputs(resetToken, newPassword, confirmPassword)) {
                try {
                    viewModel.resetPassword(resetToken, newPassword)
                }catch (ex:Exception) {
                    Toast.makeText(requireContext(), ex.message, Toast.LENGTH_LONG).show()
                }
            }

        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.resetPasswordResponse.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResponseState.Success -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                    // Navigate back to LoginFragment
                    findNavController().popBackStack()                }
                is ResponseState.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun validateInputs(
        resetToken: String,
        newPassword: String,
        confirmPassword: String
    ): Boolean {
        if (resetToken.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return false
        }

        if (newPassword != confirmPassword) {
            Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
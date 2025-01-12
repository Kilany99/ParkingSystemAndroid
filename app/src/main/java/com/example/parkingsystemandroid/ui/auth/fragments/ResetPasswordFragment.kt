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
import com.example.parkingsystemandroid.R
import com.example.parkingsystemandroid.databinding.FragmentForgotPasswordBinding
import com.example.parkingsystemandroid.viewmodel.AuthViewModel
import com.example.parkingsystemandroid.viewmodel.ResponseState


class ResetPasswordFragment : Fragment() {

    private lateinit var viewModel: AuthViewModel
    private lateinit var buttonResetPassword : Button
    private lateinit var editTextResetToken : EditText
    private lateinit var editTextNewPassword : EditText
    private lateinit var editTextConfirmPassword : EditText
    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)


        buttonResetPassword.setOnClickListener {
            val resetToken = editTextResetToken.text.toString().trim()
            val newPassword = editTextNewPassword.text.toString()
            val confirmPassword = editTextConfirmPassword.text.toString()

            if (resetToken.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newPassword != confirmPassword) {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.resetPassword(resetToken, newPassword)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.resetPasswordResponse.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResponseState.Success -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                    // Navigate back to LoginFragment
                    requireActivity().onBackPressed()
                }
                is ResponseState.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
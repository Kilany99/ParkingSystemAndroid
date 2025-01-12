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


class ForgotPasswordFragment : Fragment() {

    private lateinit var viewModel: AuthViewModel
    private lateinit var buttonSubmit: Button
    private lateinit var editTextEmail: EditText
    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]

        buttonSubmit.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            if (email.isNotEmpty()) {
                viewModel.forgotPassword(email)
            } else {
                Toast.makeText(requireContext(), "Please enter your email", Toast.LENGTH_SHORT).show()
            }
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.forgotPasswordResponse.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResponseState.Success -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                    // Navigate to ResetPasswordFragment
                    showResetPasswordFragment()
                }
                is ResponseState.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun showResetPasswordFragment() {
        val resetPasswordFragment = ResetPasswordFragment()
        // If not using the back stack
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, resetPasswordFragment)
            .commit()
            }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
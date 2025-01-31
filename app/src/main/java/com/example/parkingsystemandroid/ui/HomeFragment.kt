package com.example.parkingsystemandroid.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.parkingsystemandroid.R
import com.example.parkingsystemandroid.databinding.FragmentHomeBinding
import com.example.parkingsystemandroid.utils.TokenManager

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load user data
        val userName = TokenManager.getUserName() ?: "Valued User"
        val userEmail = TokenManager.getUserEmail() ?: ""

        binding.tvWelcome.text = "Welcome, $userName!"
        binding.tvEmail.text = userEmail

        // Set up quick action button
      //  binding.btnQuickAction.setOnClickListener {
        //    findNavController().navigate(R.id.action_homeFragment_to_parkingZonesFragment)
        //}>
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
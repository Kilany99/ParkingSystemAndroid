package com.example.parkingsystemandroid.ui.auth


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.parkingsystemandroid.MainActivity
import com.example.parkingsystemandroid.R
import com.example.parkingsystemandroid.data.model.dto.LoginDto
import com.example.parkingsystemandroid.ui.auth.fragments.ForgotPasswordFragment
import com.example.parkingsystemandroid.ui.car.CarListFragment
import com.example.parkingsystemandroid.utils.TokenManager
import com.example.parkingsystemandroid.viewmodel.AuthResponseState
import com.example.parkingsystemandroid.viewmodel.AuthViewModel


class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: AuthViewModel
    private lateinit var buttonLogin: Button
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var textViewRegister: TextView
    private lateinit var textViewForgotPassword: TextView
    private var isFragmentDisplayed = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        buttonLogin = findViewById(R.id.buttonLogin)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        textViewRegister = findViewById(R.id.textViewRegister)
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword)

        TokenManager.init(this)

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            val loginDto = LoginDto(email, password)
            viewModel.login(loginDto)
        }

        textViewRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
        textViewForgotPassword.setOnClickListener {
            // Display the ForgotPasswordFragment
            showForgotPasswordFragment()
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.authResponse.observe(this) { state ->
            when (state) {
                is AuthResponseState.Success -> {
                    // Login successful, navigate to the main activity
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                    // Start MainActivity
                    val intent = Intent(this,CarListFragment ::class.java)
                    startActivity(intent)
                    finish()
                }
                is AuthResponseState.Error -> {
                    // Show error message
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun showForgotPasswordFragment() {
        if (!isFragmentDisplayed) {
            val fragment = ForgotPasswordFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit()
            isFragmentDisplayed = true
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            isFragmentDisplayed = false
        } else {
            super.onBackPressed()
        }
    }



}
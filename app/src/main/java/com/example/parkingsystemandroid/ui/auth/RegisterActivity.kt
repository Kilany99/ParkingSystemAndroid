package com.example.parkingsystemandroid.ui.auth


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.parkingsystemandroid.R
import com.example.parkingsystemandroid.data.model.dto.RegisterDto
import com.example.parkingsystemandroid.databinding.ActivityLoginBinding
import com.example.parkingsystemandroid.databinding.ActivityRegisterBinding
import com.example.parkingsystemandroid.utils.TokenManager
import com.example.parkingsystemandroid.viewmodel.AuthResponseState
import com.example.parkingsystemandroid.viewmodel.AuthViewModel


class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: AuthViewModel
    private lateinit var buttonRegister: Button
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextName: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var textViewLogin: TextView
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        buttonRegister =findViewById(R.id.buttonRegister)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextPhone = findViewById(R.id.editTextPhone)
        editTextName = findViewById(R.id.editTextName)
        textViewLogin = findViewById(R.id.textViewLogin)
        TokenManager.init(this)
        setupClickListeners()
        observeViewModel()
    }
    private fun setupClickListeners() {
        binding.buttonRegister.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            val name = editTextName.text.toString().trim()
            val phone = editTextPhone.text.toString().trim()
            validateInputs(email,password,name,phone)
            val registerDto = RegisterDto(email, password, name, phone)
            viewModel.register(registerDto)

        }

        binding.textViewLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
    private fun validateInputs(email:String,password:String,name:String,phone:String): Boolean{
        if(email.isEmpty()||password.isEmpty()||name.isEmpty()||phone.isEmpty()){
            Toast.makeText(this,"Please fill in all fields",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun observeViewModel() {
        viewModel.authResponse.observe(this) { state ->
            when (state) {
                is AuthResponseState.Success -> {
                    // Registration successful, navigate to the main activity
                    Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                    // Start LoginActivity
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                is AuthResponseState.Error -> {
                    // Show error message
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
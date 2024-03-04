package com.example.mobile_practice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.material.textfield.TextInputEditText
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.launch
import java.util.jar.Attributes.Name

class SignUpFragment : Fragment() {
    private lateinit var etFullName: TextInputEditText
    private lateinit var etPhoneNumber: TextInputEditText
    private lateinit var etEmailAddress: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText

    private val supBase = createSupabaseClient(
        supabaseUrl = "https://thwzxkajcqlrkvtoqiuk.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InRod3p4a2FqY3Fscmt2dG9xaXVrIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MDg5MzQ0OTEsImV4cCI6MjAyNDUxMDQ5MX0.8k6uBTbNsW-GBmFvacD_8_P1m4Z1F4Q7RKZzteMrz-w"
    ) {
        install(Postgrest)
        install(Auth)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)


        etFullName = view.findViewById(R.id.EditFullName)
        etPhoneNumber = view.findViewById(R.id.EditPhone)
        etEmailAddress = view.findViewById(R.id.EditEmail)
        etPassword = view.findViewById(R.id.EditPassword)
        etConfirmPassword = view.findViewById(R.id.EditConfirmPassword)

        val btnSignUp = view.findViewById<Button>(R.id.SignUpBtn)
        btnSignUp.setOnClickListener {
            lifecycleScope.launch {
                signUpNewUser()
            }

            findNavController().navigate(
                R.id.action_signUpFragment_to_thirdAdvantage,
                null,
                navOptions {
                    anim {
                        enter = R.anim.splash_enter
                        exit = R.anim.splash_exit
                    }
                }
            )
        }

        return view
    }

    private suspend fun signUpNewUser() {

        val emailAddress = etEmailAddress.text.toString()
        val passwordET = etPassword.text.toString()

        supBase.auth.signUpWith(Email) {
            email = emailAddress
            password = passwordET
        }

        showRegistrationCompletedToast()
    }

    private fun showRegistrationCompletedToast() {
        Toast.makeText(requireContext(), "Registration completed successfully!", Toast.LENGTH_SHORT).show()
    }
}


package com.example.splitit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class LoginActivity : AppCompatActivity() {
    private lateinit var emailEdt : EditText
    private lateinit var passwdEdt : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEdt = findViewById(R.id.login_form_email)
        passwdEdt = findViewById(R.id.login_form_psswd)

        val loginBtn = findViewById<Button>(R.id.login_form_btn)
        loginBtn.setOnClickListener {
            getUserIdFromDB(
                emailEdt.text.toString(),
                passwdEdt.text.toString()
            )
        }
    }

    private fun getUserIdFromDB(email: String, passwd: String) {

    }

}
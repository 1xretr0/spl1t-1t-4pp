package com.example.splitit

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

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
            val dbHelper = Database(this)
            val selection = "${Database.EMAIL} = ? AND ${Database.PASSWORD} = ?"

            dbHelper.getUserFromDB(
                arrayOf(Database.ID_USER),
                selection,
                arrayOf(emailEdt.text.toString(), passwdEdt.text.toString()),
                null
            )
            TODO("Work with db result")
        }
    }
}
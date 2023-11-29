package com.example.splitit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

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
            println("login submitted")
            val dbHelper = Database(this)
            val selection = "${Database.EMAIL} = ? AND ${Database.PASSWORD} = ?"

            val userId = dbHelper.getUserFromDB(
                arrayOf(Database.ID_USER),
                selection,
                arrayOf(emailEdt.text.toString(), passwdEdt.text.toString()),
                null
            )
            println("found userId: $userId")

            if (userId.isEmpty()) {
                // FAILED LOGIN ATTEMPT
                val snackbar = Snackbar.make(
                    findViewById(android.R.id.content),
                    R.string.login_form_not_found,
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
            }
            else {
                // SUCCESSFUL LOGIN
                val snackbar = Snackbar.make(
                    findViewById(android.R.id.content),
                    R.string.login_form_success,
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()

                // SAVE FOUND USER ID TO SHARED PREFS
                val sharedPref = getSharedPreferences(
                    R.string.preference_file_key.toString(),
                    MODE_PRIVATE
                )
                with (sharedPref.edit()) {
                    putInt(Database.ID_USER, userId[0].toInt())
                    apply()
                }


                // SHOW MAIN LAYOUT
                val timer = Thread {
                    try {
                        Thread.sleep(1000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    } finally {
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                }
                timer.start()
            }
        }
    }
}
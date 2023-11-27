package com.example.splitit

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPref : SharedPreferences
//    private lateinit var signedUser : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPref = getSharedPreferences(
            R.string.preference_file_key.toString(),
            MODE_PRIVATE
        )
        val signedUserId = sharedPref.getInt(Database.ID_USER, 0)
        println("user id at main: $signedUserId")

        val userIdTxt = findViewById<TextView>(R.id.user_id_txt)
        userIdTxt.text = signedUserId.toString()
//
        val signOutBtn = findViewById<Button>(R.id.signout_btn)
        signOutBtn.setOnClickListener {
            with (sharedPref.edit()) {
                remove(Database.ID_USER)
                apply()
            }
            println("signed userid removed")
        }
    }
}
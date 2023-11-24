package com.example.splitit

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var myPreferences : SharedPreferences
    private var signedUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        myPreferences = getSharedPreferences("Datos", MODE_PRIVATE)

        val savedUserId = myPreferences.getInt("user_id", 0)
        if (savedUserId != 0) {
            signedUser = User(savedUserId)
        }

        val timer = Thread {
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } finally {
                if (signedUser != null)
                    // val intent = Intent(this, MainActivity::class.java)
                else
                    // val intent = Intent(this, LoginActivity::class.java)

                startActivity(intent)
            }
        }

        timer.start()
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()
        // var editor = myPreferences.edit()


    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        // var editor = myPreferences.edit()
    }
}
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
    // private lateinit var signedUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        myPreferences = getSharedPreferences("Datos", MODE_PRIVATE)
        val savedUserId = myPreferences.getInt("user_id", 0)
        println(savedUserId)

        val signedUser = if (savedUserId != 0) User(savedUserId) else null

        val timer = Thread {
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } finally {
                if (signedUser != null) {
                    startActivity(Intent(this, MainActivity::class.java))
                }
                else {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
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
package com.example.splitit

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val myPreferences = getSharedPreferences(R.string.preference_file_key.toString(), MODE_PRIVATE)
        val savedUserId = myPreferences.getInt(Database.ID_USER, 0)
        println("saved user id: $savedUserId")

        val timer = Thread {
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } finally {
                if (savedUserId != 0)
                    startActivity(Intent(this, MainActivity::class.java))
                else
                    startActivity(Intent(this, LoginActivity::class.java))
            }
        }
        timer.start()
    }
}
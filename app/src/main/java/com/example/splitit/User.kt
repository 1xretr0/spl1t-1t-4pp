package com.example.splitit

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

class User(context: Context, id: Int) {
    private var name : String
    private var email : String
    private var id : Int = 0

    private var sharedPref : SharedPreferences

    init {
        this.sharedPref = context.
            getSharedPreferences(
                R.string.preference_file_key.toString(),
                AppCompatActivity.MODE_PRIVATE
            )
        this.id = id
        this.name = "sobas"
        this.email = "sobasemail"
    }

    /**
     * getter method for user data
     * @return List containing user name and email
     */
    fun getUserData() : List<String> {
        return listOf(
            this.name,
            this.email
        )
    }

    fun signOutUser(): Boolean {
        with (sharedPref.edit()) {
            remove(Database.ID_USER)
            apply()
            println("removed shared pref")
        }
        return true
    }
}
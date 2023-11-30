package com.example.splitit

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import kotlin.properties.Delegates


class User {
    private lateinit var name : String
    private lateinit var email : String
    private var id by Delegates.notNull<Int>()
    public lateinit var loggedUser : User

    private lateinit var context : Context
    private lateinit var sharedPref : SharedPreferences

    constructor(context: Context, id: Int) {
        this.loggedUser = User(context, id)
    }

    constructor(context: Context, name: String, email: String) {
        this.name = name
        this.email = email
    }

    fun signOutUser() : Boolean {
        sharedPref = context.getSharedPreferences(
            R.string.preference_file_key.toString(),
            AppCompatActivity.MODE_PRIVATE
        )
        with (sharedPref.edit()) {
            remove(Database.ID_USER)
            apply()
        }
        return true
    }
}
package com.example.splitit

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import kotlin.properties.Delegates


class User {
    private lateinit var name : String
    private lateinit var email : String
    private var id by Delegates.notNull<Int>()

    private lateinit var context : Context
    private lateinit var sharedPref : SharedPreferences

    constructor(id: Int) {
        this.id = id
        this.name = "sobas"
        this.email = "sobasemail"
    }

    fun getUserData() : List<String> {
        return listOf(
            this.name,
            this.email
        )
    }

//    fun signOutUser() : Boolean {
//        sharedPref = context.getSharedPreferences(
//            R.string.preference_file_key.toString(),
//            AppCompatActivity.MODE_PRIVATE
//        )
//        with (sharedPref.edit()) {
//            remove(Database.ID_USER)
//            apply()
//        }
//        return true
//    }
}
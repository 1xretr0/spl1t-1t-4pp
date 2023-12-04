package com.example.splitit

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

class User(context: Context, id: Int) {
    private var name : String
    private var email : String
    private var id : Int

    private var sharedPref : SharedPreferences

    init {
        this.sharedPref = context.
            getSharedPreferences(
                R.string.preference_file_key.toString(),
                AppCompatActivity.MODE_PRIVATE
            )
        this.id = id

        val userData = this.getUserDataFromDB(context)
        println("Found user data in User init: $userData")

        this.name = userData[0].name.toString()
        this.email = userData[0].email.toString()
    }

    /**
     * gets the user name and email from db by given id
     * in constructor
     * @param context
     * @return List of strings containing the query result
     */
    private fun getUserDataFromDB(context: Context): List<Database.UserRecord> {
        val dbHelper = Database(context)
        val selection = "${Database.ID_USER} = ?"

        return dbHelper.getUserFromDB(
            arrayOf(Database.NAME, Database.EMAIL),
            selection,
            arrayOf(this.id.toString())
        )
    }

    /**
     * getter method for user data
     * @return List containing user name and email
     */
    fun getUserData(): List<String> {
        return listOf(
            this.name,
            this.email,
            this.id.toString()
        )
    }

    /**
     * deletes logged user id from shared prefs
     * @return true on success
     */
    fun signOutUser(): Boolean {
        with (sharedPref.edit()) {
            remove(Database.ID_USER)
            apply()
            println("removed shared pref")
        }
        return true
    }
}
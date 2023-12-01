package com.example.splitit

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {
    private lateinit var sharedPref : SharedPreferences
    private lateinit var loggedUser : User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPref = this.requireActivity()
            .getSharedPreferences(R.string.preference_file_key.toString(), Context.MODE_PRIVATE)

        loggedUser = User(sharedPref.getInt(Database.ID_USER, 0))
        println(loggedUser.getUserData())

//        val signoutBtn = view?.findViewById<Button>(R.id.signout_btn)
//        signoutBtn.setOnClickListener {
//            println("sign out btn clicked!")
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    private fun signOutUser(): Boolean {
        with (sharedPref.edit()) {
            remove(Database.ID_USER)
            apply()
        }
        return true
    }
}
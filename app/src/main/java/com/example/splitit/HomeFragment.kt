package com.example.splitit

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {
    private lateinit var sharedPref : SharedPreferences
    private lateinit var loggedUser : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPref = requireActivity()
            .getSharedPreferences(
                R.string.preference_file_key.toString(),
                AppCompatActivity.MODE_PRIVATE
            )
        loggedUser = User(requireActivity(), sharedPref.getInt(Database.ID_USER, 0))
        println("User obj at home frag: ${loggedUser.getUserData()}")

        val dbHelper = Database(requireContext())
        val selection = "${Database.ID_USER} = ?"

        val splitsFromLoggedUser = dbHelper.getSplitsFromDB(
            arrayOf(Database.ID_SPLIT, Database.STORE_NAME, Database.TOTAL),
            selection,
            arrayOf(loggedUser.getUserData()[0]),
            null
        )
        println("found splits: $splitsFromLoggedUser")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}
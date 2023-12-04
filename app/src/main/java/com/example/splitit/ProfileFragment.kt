package com.example.splitit

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {
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
        println("User obj at profile frag: ${loggedUser.getUserData()}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val usernameTxt = view.findViewById<TextView>(R.id.profile_txt2)
        val userEmailTxt = view.findViewById<TextView>(R.id.profile_txt3)

        usernameTxt.text = loggedUser.getUserData()[0]
        userEmailTxt.text = loggedUser.getUserData()[1]

        // BUTTON TO COPY TEXT TO CLIPBOARD
        val profileBtn1 = view.findViewById<Button>(R.id.profile_btn_1)
        profileBtn1.setOnClickListener {
            // Copy text to clipboard
            val clipboardManager = requireActivity()
                .getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText(Database.ID_USER, loggedUser.getUserData()[2])
            clipboardManager.setPrimaryClip(clipData)

            // Display toast notification
            Toast.makeText(
                requireContext(),
                R.string.profile_btn1_toast,
                Toast.LENGTH_SHORT
            ).show()
        }

        val profileBtn2 = view.findViewById<Button>(R.id.profile_btn2)
        profileBtn2.setOnClickListener {

        }

        // SIGN OUT BUTTON
        val signoutBtn = view.findViewById<Button>(R.id.signout_btn)
        signoutBtn.setOnClickListener {
            println("clicked sign out btn")
            loggedUser.signOutUser()
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
        }

        return view
    }
}
package com.example.splitit

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

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

        usernameTxt.text = loggedUser.getUserName()
        userEmailTxt.text = loggedUser.getUserEmail()

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

        // ADD FRIEND BUTTON
        val profileBtn2 = view.findViewById<Button>(R.id.profile_btn2)
        profileBtn2.setOnClickListener {
            // Create an AlertDialog Builder
            val builder = AlertDialog.Builder(requireContext())

            // Set the dialog title and message
            builder.setTitle(R.string.profile_btn2_alert_title)
            builder.setMessage(R.string.profile_btn2_alert_msg)

            // Create an EditText to input the friend's ID
            val input = EditText(requireContext())
            input.inputType = InputType.TYPE_CLASS_NUMBER // Set the input type to numbers

            // Set the EditText as the dialog view
            builder.setView(input)

            // Set up the buttons for the dialog
            builder.setPositiveButton(R.string.profile_btn2_alert_btn_pos) { _, _ ->
                val friendId = input.text.toString()
                val dbHelper = Database(requireContext())
                val insertResult = dbHelper.insertIntoFriends(
                    loggedUser.getUserId(),
                    friendId
                )
                println("insert result: $insertResult")
                val existingFriends = dbHelper.getFriendsFromDB()
                println("existing friends: $existingFriends")

                // Validate insertResult and show Snackbar accordingly
                val insertResultMsg = if (insertResult != -1L) {
                    // Insertion successful
                    getString(R.string.profile_add_success)
                } else {
                    // Insertion failed or friend already added
                    getString(R.string.profile_add_failure)
                }
                Snackbar.make(
                    requireView(),
                    insertResultMsg,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            builder.setNegativeButton(R.string.profile_btn2_alert_btn_neg) { dialog, _ ->
                dialog.cancel() // Dismiss the dialog when "Cancel" is clicked
            }

            // Create and show the AlertDialog
            val dialog = builder.create()
            dialog.show()
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
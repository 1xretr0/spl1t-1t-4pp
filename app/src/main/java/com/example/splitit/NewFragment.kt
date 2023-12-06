package com.example.splitit

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class NewFragment : Fragment() {
    private lateinit var sharedPref : SharedPreferences
    private lateinit var loggedUser : User
    private lateinit var dbHelper : Database

    private lateinit var recyclerView: RecyclerView
    private lateinit var newFriendAdapter: NewFriendAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPref = requireActivity()
            .getSharedPreferences(
                R.string.preference_file_key.toString(),
                AppCompatActivity.MODE_PRIVATE
            )
        loggedUser = User(requireActivity(), sharedPref.getInt(Database.ID_USER, 0))
        println("User obj at home frag: ${loggedUser.getUserData()}")

        dbHelper = Database(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_new, container, false)

        val addedFriendsList = ArrayList<Database.FriendRecord>()
        val addFriendTxt = view.findViewById<EditText>(R.id.new_split_edt2)
        val totalEdt = view.findViewById<EditText>(R.id.new_split_edt1)

        // ADD FRIEND BUTTON LISTENER
        val addFriendBtn = view.findViewById<Button>(R.id.add_friends_btn)
        addFriendBtn.setOnClickListener {
            val newFriendName = addFriendTxt.text.toString()
            // GET TOTAL AMOUNT AT THE MOMENT
            var totalAmount = totalEdt.text.toString()

            if (totalAmount.isBlank() || totalAmount.isEmpty()) {
                // Create an AlertDialog Builder
                val builder = AlertDialog.Builder(requireContext())

                // Set the dialog title and message
                builder.setTitle(R.string.new_menu_itm)
                builder.setMessage(R.string.new_friend_sb_empty)

                builder.setNegativeButton(android.R.string.ok) { dialog, _ ->
                    dialog.cancel() // Dismiss the dialog when "Cancel" is clicked
                    totalEdt.requestFocus()
                }

                // Create and show the AlertDialog
                val dialog = builder.create()
                dialog.show()
            }
            else if (newFriendName.isNotBlank() && newFriendName.isNotEmpty()) {
                addFriendTxt.text.clear()
                // ADD NEW FRIEND INTO FRIENDS ADDED LIST
                addedFriendsList.add(Database.FriendRecord(
                    null,
                    null,
                    null,
                    newFriendName
                ))

                if (totalAmount.isEmpty() || totalAmount.isBlank())
                    totalAmount = "0"

                val numberOfFriends = addedFriendsList.size
                val splitAmount = totalAmount.toFloat() / numberOfFriends

                // SPLIT AMOUNT BETWEEN THE ADDED FRIENDS
                for (friend in addedFriendsList) {
                    friend.splitAmount = splitAmount
                    friend.total = totalAmount.toFloat()
                }

                // CREATE VIEW OF ADDED FRIEND
                recyclerView = view.findViewById(R.id.recycler_view_2)
                newFriendAdapter = NewFriendAdapter()
                recyclerView.adapter = newFriendAdapter
                recyclerView.setHasFixedSize(true)

                val layoutManager = GridLayoutManager(requireContext(), 1)
                recyclerView.layoutManager = layoutManager

                newFriendAdapter.addFriendData(
                    addedFriendsList
                )
            }
        }

        // PAY BUTTON LISTENER
        view.findViewById<Button>(R.id.button_pay).setOnClickListener {
            // TODO(save new split and show payed alertDialog)
        }

        return view
    }
}
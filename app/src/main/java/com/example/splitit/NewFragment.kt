package com.example.splitit

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
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

        // ADD FRIEND BUTTON LISTENER
        val addFriendBtn = view.findViewById<Button>(R.id.add_friends_btn)
        addFriendBtn.setOnClickListener {
            // GET FRIEND'S NAME TO ADD AND CLEAR
            val addFriendTxt = view.findViewById<EditText>(R.id.new_split_edt2)
            val newFriendName = addFriendTxt.text.toString()
            addFriendTxt.text.clear()

            // CREATE VIEW OF ADDED FRIEND
            recyclerView = view.findViewById(R.id.recycler_view_2)
            newFriendAdapter = NewFriendAdapter()
            recyclerView.adapter = newFriendAdapter
            recyclerView.setHasFixedSize(true)

            val layouManager = GridLayoutManager(requireContext(), 1)
            recyclerView.layoutManager = layouManager

            // PASS NEW FRIEND DATA TO ADAPTER
            newFriendAdapter.addFriendData(
                listOf(Database.FriendRecord(
                    null,
                    null,
                    null,
                    newFriendName
                ))
            )
        }

        return view
    }
}
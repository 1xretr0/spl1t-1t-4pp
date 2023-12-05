package com.example.splitit

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {
    private lateinit var sharedPref : SharedPreferences
    private lateinit var loggedUser : User
    private lateinit var dbHelper : Database

    private lateinit var recyclerView: RecyclerView
    private lateinit var homeSplitsAdapter: HomeSplitsAdapter

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
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // GET USER SPLITS FROM DB
        val selection = "${Database.ID_USER} = ?"
        val splitsFromLoggedUser = dbHelper.getSplitsFromDB(
            null,
            selection,
            arrayOf(loggedUser.getUserId())
        )
        println("found splits: $splitsFromLoggedUser")

        // CREATE VIEW OF OBTAINED SPLITS
        recyclerView = view.findViewById(R.id.recycler_view)
        homeSplitsAdapter = HomeSplitsAdapter()
        recyclerView.adapter = homeSplitsAdapter
        recyclerView.setHasFixedSize(true)

        val layoutManager = GridLayoutManager(requireContext(), 1)
        recyclerView.layoutManager = layoutManager

        // PASS OBTAINED SPLITS TO ADAPTER
        homeSplitsAdapter.addSplitsData(splitsFromLoggedUser)

        return view
    }
}
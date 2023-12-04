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
    private lateinit var dbHelper : Database

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

//        splitsFromLoggedUser.forEach { splitRecord ->
//            val inflatedLayout = inflater.inflate(R.id.splits_linear_lyt1, null)
//
//            // Populate the inflated layout with data from the SplitRecord
//            inflatedLayout.findViewById<TextView>(R.id.home_split_txt2).text = splitRecord.storeName
//            inflatedLayout.findViewById<TextView>(R.id.home_split_txt3).text = splitRecord.status
//            inflatedLayout.findViewById<TextView>(R.id.home_split_txt4).text = splitRecord.total.toString()
//
//            // Add the inflated layout to the LinearLayout
//            splitsLinearLayout.addView(inflatedLayout)
//        }

        return view
    }
}
package com.example.splitit

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.splitit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPref : SharedPreferences
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        sharedPref = getSharedPreferences(
            R.string.preference_file_key.toString(),
            MODE_PRIVATE
        )

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.home_menu_itm -> replaceFragment(HomeFragment())
                R.id.new_menu_itm -> replaceFragment(NewFragment())
                R.id.profile_menu_itm -> {
                    replaceFragment(ProfileFragment())
                    // ProfileFragment.newInstance(sharedPref.getInt(Database.ID_USER, 0))
                }
                else -> {
                    /* TODO: else not yet implemented */
                }
            }
            true
        }

        val signedUserId = sharedPref.getInt(Database.ID_USER, 0)
        println("user id at main: $signedUserId")
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.main_frame_lyt, fragment)
        fragmentTransaction.commit()
    }
}
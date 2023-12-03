/**
 * @author Andres Orihuela Otero
 * @author Mauricio Solano Najera
 * @author Sebastian Moran Hernandez
 * PROYECTO FINAL PROGRAMACION DISPOSITIVOS MOVILES
 * UPAEP OTONO 2023
 */

package com.example.splitit

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.splitit.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.util.Objects
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPref : SharedPreferences
    private lateinit var binding : ActivityMainBinding

    private var currentFragment : Fragment? = null

    private var sensorManager: SensorManager? = null
    private var acceleration = 0f
    private var currentAcceleration = 0f
    private var lastAcceleration = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        // NAVIGATION BAR
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.home_menu_itm -> {
                    currentFragment = HomeFragment()
                    replaceFragment(currentFragment!!)
                }
                R.id.new_menu_itm -> {
                    currentFragment = NewFragment()
                    replaceFragment(currentFragment!!)
                }
                R.id.profile_menu_itm -> {
                    currentFragment = ProfileFragment()
                    replaceFragment(currentFragment!!)
                }
                else -> {
                    /* TODO: else not yet implemented */
                }
            }
            true
        }

        // Getting the Sensor Manager instance
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        Objects.requireNonNull(sensorManager)!!
            .registerListener(sensorListener, sensorManager!!
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)

        acceleration = 10f
        currentAcceleration = SensorManager.GRAVITY_EARTH
        lastAcceleration = SensorManager.GRAVITY_EARTH

        // SHARED PREFERENCES
        sharedPref = getSharedPreferences(
            R.string.preference_file_key.toString(),
            MODE_PRIVATE
        )
        val signedUserId = sharedPref.getInt(Database.ID_USER, 0)
        println("user id at main: $signedUserId")
    }

    private val sensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {

            // Fetching x,y,z values
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            lastAcceleration = currentAcceleration

            // Getting current accelerations
            // with the help of fetched x,y,z values
            currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val delta: Float = currentAcceleration - lastAcceleration
            acceleration = acceleration * 0.9f + delta

            // GO TO NEW FRAGMENT
            if (acceleration > 12) {
                val snackbar = Snackbar.make(
                    findViewById(android.R.id.content),
                    R.string.main_snackbar_shake,
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
                replaceFragment(NewFragment())
            }
        }
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    override fun onResume() {
        sensorManager?.registerListener(sensorListener, sensorManager!!.getDefaultSensor(
            Sensor .TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL
        )
        super.onResume()
    }

    override fun onPause() {
        sensorManager!!.unregisterListener(sensorListener)
        super.onPause()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        // Check if the current fragment is not the HomeFragment
        // If not, navigate back to the HomeFragment
        if (currentFragment !is HomeFragment) {
            currentFragment = HomeFragment()
            replaceFragment(currentFragment!!)
        } else {
            // GO TO ANDROID HOME
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.main_frame_lyt, fragment)
        fragmentTransaction.commit()
    }
}
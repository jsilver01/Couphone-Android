package com.kuit.couphone

import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.findFragment
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.kuit.couphone.databinding.ActivityMainBinding
import com.kuit.couphone.ui.home.HomeFragment
import com.kuit.couphone.ui.settings.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val homeFragment: HomeFragment by lazy { HomeFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()

        binding.fab.setOnClickListener {
            navigateToHomeFragment()
        }

    }

    private fun initBottomNavigation() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, MyLocationFragment())
            .commitAllowingStateLoss()

        binding.fab.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitAllowingStateLoss()
        }
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    val fragment = supportFragmentManager.findFragmentById(R.id.main_frm)
                    if(binding.bottomNavigationView.selectedItemId != it.itemId|| fragment is HomeFragment) {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, MyLocationFragment())
                            .commitAllowingStateLoss()
                        return@setOnItemSelectedListener true
                    }
                }

                R.id.navigation_settings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SettingsFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }


    private fun navigateToHomeFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, homeFragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    override fun onBackPressed() {
        val fragmentStack = supportFragmentManager.fragments
        if (fragmentStack.isNotEmpty()) {
            val topFragment = fragmentStack.last()
            if (topFragment is HomeFragment || topFragment is SettingsFragment) {
                navigateToHomeFragment()
            } else {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }
}


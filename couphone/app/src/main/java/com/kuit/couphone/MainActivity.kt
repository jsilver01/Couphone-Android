package com.kuit.couphone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun initBottomNavigation() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, homeFragment)
            .commitAllowingStateLoss()

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navigateToHomeFragment()
                    true
                }
                R.id.navigation_settings -> {
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.main_frm, SettingsFragment())
                    transaction.addToBackStack(null)
                    transaction.commitAllowingStateLoss()
                    true
                }
                else -> false
            }
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

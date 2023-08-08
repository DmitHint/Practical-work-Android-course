package com.example.jetpack_compose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import com.example.jetpack_compose.databinding.ActivityMainBinding
import com.example.jetpack_compose.fragments.CharacterListFragment
import com.example.jetpack_compose.fragments.LocationListFragment
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val fragment = when (tab?.text) {
                    "Characters" -> CharacterListFragment()
                    "Locations" -> LocationListFragment()
                    else -> return
                }

                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commitNow()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Characters"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Locations"))

        binding.tabLayout.setTabTextColors(
            ContextCompat.getColor(this, R.color.teal_700),
            ContextCompat.getColor(this, R.color.teal_700)
        )
        binding.tabLayout.setBackgroundColor(
            ContextCompat.getColor(this, R.color.list_background),
        )

    }
}

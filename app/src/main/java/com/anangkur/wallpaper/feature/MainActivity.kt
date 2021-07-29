package com.anangkur.wallpaper.feature

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.anangkur.wallpaper.R
import com.anangkur.wallpaper.databinding.ActivityMainBinding
import com.anangkur.wallpaper.presentation.getHomeFragment
import com.anangkur.wallpaper.presentation.getSavedFragment
import com.anangkur.wallpaper.presentation.getSearchFragment

class MainActivity: AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    var activeTabId = R.id.home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun start() {
        setupNavigationView()
        binding.bottomNav.selectedItemId = activeTabId
    }

    private fun setupNavigationView() {
        binding.bottomNav.setOnNavigationItemSelectedListener {
            activeTabId = it.itemId
            when (it.itemId) {
                R.id.home -> {
                    setFragment(getHomeFragment())
                    true
                }
                R.id.search -> {
                    setFragment(getSearchFragment())
                    true
                }
                R.id.saved -> {
                    setFragment(getSavedFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, fragment).commit()
    }
}

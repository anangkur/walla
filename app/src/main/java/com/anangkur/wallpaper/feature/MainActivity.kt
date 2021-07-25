package com.anangkur.wallpaper.feature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.anangkur.wallpaper.R
import com.anangkur.wallpaper.databinding.ActivityMainBinding
import com.anangkur.wallpaper.utils.getHomeFragment
import com.anangkur.wallpaper.utils.getSavedFragment
import com.anangkur.wallpaper.utils.getSearchFragment

class MainActivity: AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupNavigationView()
        binding.bottomNav.selectedItemId = R.id.home
    }

    private fun setupNavigationView() {
        binding.bottomNav.setOnNavigationItemSelectedListener {
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

package com.anangkur.wallpaper.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anangkur.wallpaper.databinding.ActivitySplashBinding

class SplashActivity: AppCompatActivity(){

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}

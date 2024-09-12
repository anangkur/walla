package com.anangkur.wallpaper

import android.app.Application
import com.anangkur.wallpaper.injection.Injector

class WallaApplication : Application() {

    val injector by lazy { Injector(this, BuildConfig.UNSPLASH_ENDPOINT) }
}
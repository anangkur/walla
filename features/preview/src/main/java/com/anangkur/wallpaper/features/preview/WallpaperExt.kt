package com.anangkur.wallpaper.features.preview

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap

fun Context.setWallpaperDevice(bitmap: Bitmap) {
    WallpaperManager.getInstance(this).setBitmap(bitmap)
}
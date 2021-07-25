package com.anangkur.wallpaper.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

private const val HOME_FRAGMENT = "com.anangkur.wallpaper.features.home.HomeFragment"
private const val SEARCH_FRAGMENT = "com.anangkur.wallpaper.features.search.SearchFragment"
private const val SAVED_FRAGMENT = "com.anangkur.wallpaper.features.saved.SavedFragment"
private const val PREVIEW_DIALOG_FRAGMENT = "com.anangkur.wallpaper.features.preview.PreviewDialog"
private const val PREVIEW_ACTIVITY = "com.anangkur.wallpaper.features.preview.PreviewActivity"

const val ARGS_TITLE = "title"
const val ARGS_CREATOR = "creator"
const val ARGS_IMAGE_URL = "imageUrl"

private fun getFragment(className: String) = Class.forName(className).newInstance() as Fragment
private fun getDialogFragment(className: String) = Class.forName(className).newInstance() as DialogFragment

fun getHomeFragment() = getFragment(HOME_FRAGMENT)
fun getSearchFragment() = getFragment(SEARCH_FRAGMENT)
fun getSavedFragment() = getFragment(SAVED_FRAGMENT)
fun getPreviewDialog(
    title: String,
    creator: String,
    imageUrl: String
) = getDialogFragment(PREVIEW_DIALOG_FRAGMENT).apply {
    arguments = Bundle().apply {
        putString(ARGS_TITLE, title)
        putString(ARGS_CREATOR, creator)
        putString(ARGS_IMAGE_URL, imageUrl)
    }
}

fun Context.startPreviewActivity(
    title: String,
    creator: String,
    imageUrl: String
) {
    startActivity(Intent(this, Class.forName(PREVIEW_ACTIVITY)).apply {
        putExtra(ARGS_TITLE, title)
        putExtra(ARGS_CREATOR, creator)
        putExtra(ARGS_IMAGE_URL, imageUrl)
    })
}
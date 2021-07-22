package com.anangkur.wallpaper.utils

import androidx.fragment.app.Fragment

private const val HOME_FRAGMENT = "com.anangkur.wallpaper.features.home.HomeFragment"
private const val SEARCH_FRAGMENT = "com.anangkur.wallpaper.features.search.SearchFragment"
private const val SAVED_FRAGMENT = "com.anangkur.wallpaper.features.saved.SavedFragment"

private fun getFragment(className: String) = Class.forName(className).newInstance() as Fragment

fun getHomeFragment() = getFragment(HOME_FRAGMENT)
fun getSearchFragment() = getFragment(SEARCH_FRAGMENT)
fun getSavedFragment() = getFragment(SAVED_FRAGMENT)
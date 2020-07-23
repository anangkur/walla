package com.anangkur.wallpaper.news.home

import com.anangkur.wallpaper.model.ArticleIntent

interface HomeActionListener {
    fun onClickItem(data: ArticleIntent)
}
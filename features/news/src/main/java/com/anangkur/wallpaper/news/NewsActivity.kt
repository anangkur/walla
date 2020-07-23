package com.anangkur.wallpaper.news

import androidx.appcompat.widget.Toolbar
import com.anangkur.wallpaper.news.databinding.ActivityNewsBinding
import com.anangkur.wallpaper.base.BaseActivity
import com.anangkur.wallpaper.utils.obtainViewModel
import com.anangkur.wallpaper.presentation.features.news.NewsViewModel
import com.anangkur.wallpaper.R as appR

class NewsActivity: BaseActivity<ActivityNewsBinding, NewsViewModel>() {

    override val mLayout: ActivityNewsBinding
        get() = ActivityNewsBinding.inflate(layoutInflater)
    override val mViewModel: NewsViewModel
        get() = obtainViewModel(NewsViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = findViewById(appR.id.toolbar)
}
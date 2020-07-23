package com.anangkur.wallpaper.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<V: ViewBinding, T: ViewModel?>: AppCompatActivity(){

    abstract val mLayout: V
    abstract val mViewModel: T?
    abstract val mToolbar: Toolbar?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mLayout.root)

        setupToolbar(mToolbar)
    }

    private fun setupToolbar(toolbar: Toolbar?){
        toolbar?.let { setSupportActionBar(it) }
    }
}
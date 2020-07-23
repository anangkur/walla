package com.anangkur.wallpaper.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.anangkur.wallpaper.R
import com.anangkur.wallpaper.utils.gone
import com.anangkur.wallpaper.utils.visible
import com.anangkur.materialloadingbutton.MaterialLoadingButton

class BaseErrorView(context: Context, attrs: AttributeSet): RelativeLayout(context, attrs){

    private var ivError: ImageView
    private var tvError: TextView
    private var btnError: MaterialLoadingButton
    private var pbError: ProgressBar

    init {
        val v = LayoutInflater.from(context).inflate(R.layout.layout_error, null)
        ivError = v.findViewById(R.id.iv_error)
        tvError = v.findViewById(R.id.tv_error)
        btnError = v.findViewById(R.id.btn_error)
        pbError = v.findViewById(R.id.pb_error)
        addView(v)
    }

    fun showProgress(){
        pbError.visible()
        ivError.gone()
        tvError.gone()
        btnError.gone()
    }

    fun showError(errorMessage: String, buttonErrorString: String = context.getString(R.string.btn_muat_ulang), errorType: Int){
        endProgress()
        ivError.visible()
        tvError.visible()
        tvError.text = errorMessage
        btnError.setText(buttonErrorString)
        when(errorType){
            ERROR_NULL_DATA -> {
                btnError.gone()
            }
            ERROR_GENERAL -> {
                btnError.visible()
            }
        }
    }

    fun endProgress(){
        pbError.gone()
    }

    fun setRetryClickListener(todo: (view: View?) -> Unit) {
        btnError.setOnClickListener(null)
        btnError.setOnClickListener { view ->
            todo(view)
        }
    }

    companion object{
        const val ERROR_NULL_DATA = 0
        const val ERROR_GENERAL = 1
    }
}
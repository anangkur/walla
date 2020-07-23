package com.anangkur.wallpaper.news.originalNews

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.anangkur.wallpaper.base.BaseErrorView
import com.anangkur.wallpaper.base.BaseFragment
import com.anangkur.wallpaper.news.NewsActivity
import com.anangkur.wallpaper.news.R
import com.anangkur.wallpaper.news.databinding.FragmentOriginalNewsBinding
import com.anangkur.wallpaper.presentation.features.news.NewsViewModel
import com.anangkur.wallpaper.R as appR
import com.anangkur.wallpaper.utils.gone
import com.anangkur.wallpaper.utils.visible

class OriginalNewsFragment: BaseFragment<FragmentOriginalNewsBinding, NewsViewModel>() {

    override val mViewModel: NewsViewModel
        get() = (requireActivity() as NewsActivity).mViewModel
    override val mToolbar: Toolbar?
        get() = (requireActivity() as NewsActivity).mToolbar

    private var isSuccessLoadUrl = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupWebView(mViewModel.originalNewsUrl)
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_original_news, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.menu_open_browser -> {
                val webPage = Uri.parse(mViewModel.originalNewsUrl)
                val intent = Intent(Intent.ACTION_VIEW, webPage)
                if (intent.resolveActivity(requireActivity().packageManager) != null) {
                    startActivity(intent)
                }
                true
            }
            else -> false
        }
    }

    private fun setupWebView(url: String){
        mLayout.wvOriginalNews.loadUrl(url)
        mLayout.wvOriginalNews.webViewClient = object: WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                isSuccessLoadUrl = true
                mLayout.wvOriginalNews.gone()
                mLayout.evOriginalNews.showProgress()
                mLayout.evOriginalNews.visible()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (isSuccessLoadUrl){
                    mLayout.wvOriginalNews.visible()
                    mLayout.evOriginalNews.gone()
                    mLayout.evOriginalNews.endProgress()
                }
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                isSuccessLoadUrl = false
                mLayout.wvOriginalNews.gone()
                mLayout.evOriginalNews.showError(
                    errorMessage = getString(appR.string.error_default),
                    errorType = BaseErrorView.ERROR_GENERAL
                )
                mLayout.evOriginalNews.setRetryClickListener {
                    view?.reload()
                }
                mLayout.evOriginalNews.visible()
            }
        }
    }

    override fun setupToolbar(toolbar: Toolbar?) {
        toolbar?.title = mViewModel.selectedNews?.title
        toolbar?.navigationIcon = ContextCompat.getDrawable(requireContext(), appR.drawable.ic_arrow_back_black_24dp)
        toolbar?.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

    override fun setupView(container: ViewGroup?): FragmentOriginalNewsBinding {
        return FragmentOriginalNewsBinding.inflate(LayoutInflater.from(requireContext()), container, false)
    }
}
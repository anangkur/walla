package com.anangkur.wallpaper.features.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.wallpaper.R as APP_R
import com.anangkur.wallpaper.feature.search.R as SEARCH_R
import com.anangkur.wallpaper.feature.search.databinding.ActivitySearchResultBinding
import com.anangkur.wallpaper.presentation.ARGS_COLOR
import com.anangkur.wallpaper.presentation.getPreviewDialog
import com.anangkur.wallpaper.utils.obtainViewModel
import com.anangkur.wallpaper.utils.showSnackbarShort
import com.anangkur.wallpaper.BuildConfig
import com.anangkur.wallpaper.domain.model.Wallpaper
import com.anangkur.wallpaper.features.search.di.ViewModelFactory
import com.anangkur.wallpaper.utils.provideRepository

class SearchResultActivity : AppCompatActivity() {

    companion object {
        private fun Int.toColorString() = when (this) {
            SEARCH_R.color.black -> "black"
            SEARCH_R.color.white -> "white"
            SEARCH_R.color.yellow -> "yellow"
            SEARCH_R.color.orange -> "orange"
            SEARCH_R.color.red -> "red"
            SEARCH_R.color.purple -> "purple"
            SEARCH_R.color.magenta -> "magenta"
            SEARCH_R.color.green -> "green"
            SEARCH_R.color.teal -> "teal"
            SEARCH_R.color.blue -> "blue"
            else -> null
        }
    }

    private lateinit var binding: ActivitySearchResultBinding

    private lateinit var searchResultAdapter: SearchResultAdapter

    private lateinit var searchResultViewModel: SearchResultViewModel

    private var color: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchResultBinding.inflate(layoutInflater)

        setContentView(binding.root)

        getIntentExtra()
        setupSearch()
        setupRecyclerResult()
        setupViewModel()
        observeViewModel()
        if (color != -1) searchResultViewModel.searchWallpaper(BuildConfig.UNSPLASH_ACCESS_KEY, color = color.toColorString())
    }

    private fun getIntentExtra() {
        color = intent.getIntExtra(ARGS_COLOR, -1)
    }

    private fun setupSearch() {
        binding.searchResult.visibility = if (color == -1) View.VISIBLE else View.GONE
        binding.searchResult.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.orEmpty().isNotEmpty()) searchResultViewModel.searchWallpaper(BuildConfig.UNSPLASH_ACCESS_KEY, query = query)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.orEmpty().isEmpty()) {}
                return true
            }
        })
    }

    private fun setupRecyclerResult() {
        searchResultAdapter = SearchResultAdapter {
            getPreviewDialog(
                id = it.id,
                title = it.title,
                creator = it.creator,
                imageUrl = it.imageUrl,
                isSaved = it.isSaved,
                thumbnailUrl = it.thumbnailUrl
            ).show(supportFragmentManager, "")
        }
        binding.recyclerResult.apply {
            adapter = searchResultAdapter
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(this@SearchResultActivity, RecyclerView.VERTICAL, false)
        }
    }

    private fun setLoadingResult() {
        binding.flipperResult.displayedChild = 1
    }

    private fun setErrorResult(errorMessage: String) {
        binding.flipperResult.displayedChild = 2
        binding.tvErrorResult.text = errorMessage
        showSnackbarShort(errorMessage)
        binding.btnRefresh.setOnClickListener {  }
    }

    private fun setSuccessResult(wallpapers: List<Wallpaper>) {
        binding.flipperResult.displayedChild = 0
        searchResultAdapter.setItems(wallpapers)
    }

    private fun setEmptyResult() {
        binding.flipperResult.displayedChild = 2
        binding.tvErrorResult.text = getString(APP_R.string.error_empty)
        binding.ivRefresh.setImageResource(APP_R.drawable.ic_problem)
        binding.btnRefresh.setOnClickListener {  }
    }

    private fun setupViewModel() {
        searchResultViewModel = obtainViewModel(
            SearchResultViewModel::class.java,
            ViewModelFactory.getInstance(provideRepository()),
        )
    }

    private fun observeViewModel() {
        searchResultViewModel.loadingSearchResult.observe(this, Observer {
            if (it) setLoadingResult()
        })
        searchResultViewModel.errorSearchResult.observe(this, Observer {
            setErrorResult(it.ifEmpty { getString(APP_R.string.error_default) })
        })
        searchResultViewModel.searchResult.observe(this, Observer {
            if (it.isEmpty()) setEmptyResult() else setSuccessResult(it)
        })
    }
}
package com.anangkur.wallpaper.features.search

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.anangkur.wallpaper.R
import com.anangkur.wallpaper.data.model.Wallpaper
import com.anangkur.wallpaper.feature.search.databinding.ActivitySearchResultBinding
import com.anangkur.wallpaper.presentation.ARGS_COLOR
import com.anangkur.wallpaper.presentation.getPreviewDialog
import com.anangkur.wallpaper.utils.showSnackbarShort

class SearchResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchResultBinding

    private lateinit var searchResultAdapter: SearchResultAdapter

    private var color: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchResultBinding.inflate(layoutInflater)

        getIntentExtra()
        setupSearch()
        setupRecyclerResult()
    }

    private fun getIntentExtra() {
        color = intent.getIntExtra(ARGS_COLOR, -1)
    }

    private fun setupSearch() {
        binding.searchResult.visibility = if (color == -1) View.VISIBLE else View.GONE
        binding.searchResult.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.orEmpty().isNotEmpty()) {}
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
            layoutManager = LinearLayoutManager(this@SearchResultActivity, LinearLayout.VERTICAL, false)
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
        binding.tvErrorResult.text = getString(R.string.error_empty)
        binding.ivRefresh.setImageResource(R.drawable.ic_problem)
        binding.btnRefresh.setOnClickListener {  }
    }
}
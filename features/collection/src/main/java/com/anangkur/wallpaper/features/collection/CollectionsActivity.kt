package com.anangkur.wallpaper.features.collection

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.anangkur.wallpaper.BuildConfig
import com.anangkur.wallpaper.data.model.Wallpaper
import com.anangkur.wallpaper.features.collection.databinding.ActivityCollectionBinding
import com.anangkur.wallpaper.presentation.ARGS_ID
import com.anangkur.wallpaper.presentation.ARGS_TITLE
import com.anangkur.wallpaper.presentation.features.collection.CollectionViewModel
import com.anangkur.wallpaper.presentation.getPreviewDialog
import com.anangkur.wallpaper.utils.obtainViewModel
import com.anangkur.wallpaper.utils.showSnackbarShort
import com.anangkur.wallpaper.R as APP_R

class CollectionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCollectionBinding

    private lateinit var collectionAdapter: CollectionAdapter

    private lateinit var collectionViewModel: CollectionViewModel

    private lateinit var id: String
    private lateinit var title: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCollectionBinding.inflate(layoutInflater)

        setContentView(binding.root)

        getIntentExtra()

        setupToolbar()
        setupRecycler()
        setupViewModel()
        setupSwipeRefresh()

        observerViewModel()

        collectionViewModel.fetchCollectionPhotos(clientId = BuildConfig.UNSPLASH_ACCESS_KEY, collectionId = id)
    }

    private fun getIntentExtra() {
        id = intent.getStringExtra(ARGS_ID).orEmpty()
        title = intent.getStringExtra(ARGS_TITLE).orEmpty()
    }

    private fun setupRecycler() {
        collectionAdapter = CollectionAdapter {
            getPreviewDialog(
                id = it.id,
                title = it.title,
                creator = it.creator,
                imageUrl = it.imageUrl,
                isSaved = it.isSaved,
                thumbnailUrl = it.thumbnailUrl
            ).show(supportFragmentManager, "")
        }
        binding.recyclerCollection.apply {
            adapter = collectionAdapter
            layoutManager = LinearLayoutManager(this@CollectionsActivity, LinearLayout.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        binding.toolbar.title = title.ifEmpty { getString(APP_R.string.app_name) }
        setSupportActionBar(binding.toolbar)
    }

    private fun setupViewModel() {
        collectionViewModel = obtainViewModel(CollectionViewModel::class.java)
    }

    private fun observerViewModel() {
        collectionViewModel.collectionPhotos.observe(this, Observer {
            if (it.isEmpty()) setEmptyCollection() else setSuccessCollections(it)
        })
        collectionViewModel.loadingCollectionPhotos.observe(this, Observer {
            if (it) setLoadingCollection()
        })
        collectionViewModel.errorCollectionPhotos.observe(this, Observer {
            setErrorCollection(it)
        })
    }

    private fun setLoadingCollection() {
        binding.flipperCollection.displayedChild = 1
    }

    private fun setErrorCollection(errorMessage: String) {
        binding.flipperCollection.displayedChild = 2
        binding.tvErrorCollection.text = errorMessage.ifEmpty { getString(APP_R.string.error_default) }
        showSnackbarShort(errorMessage)
        binding.ivRefresh.setImageResource(APP_R.drawable.ic_refresh)
        binding.btnRefresh.setOnClickListener {
            collectionViewModel.fetchCollectionPhotos(clientId = BuildConfig.UNSPLASH_ACCESS_KEY, collectionId = id)
        }
    }

    private fun setEmptyCollection() {
        binding.flipperCollection.displayedChild = 2
        binding.tvErrorCollection.text = getString(APP_R.string.error_empty)
        binding.ivRefresh.setImageResource(APP_R.drawable.ic_problem)
        binding.btnRefresh.setOnClickListener {
            collectionViewModel.fetchCollectionPhotos(clientId = BuildConfig.UNSPLASH_ACCESS_KEY, collectionId = id)
        }
    }

    private fun setSuccessCollections(collectionPhotos: List<Wallpaper>) {
        binding.flipperCollection.displayedChild = 0
        collectionAdapter.setItems(collectionPhotos)
    }

    private fun setupSwipeRefresh() {
        binding.swipeCollection.setOnRefreshListener {
            collectionViewModel.fetchCollectionPhotos(clientId = BuildConfig.UNSPLASH_ACCESS_KEY, collectionId = id)
            binding.swipeCollection.isRefreshing = false
        }
    }
}
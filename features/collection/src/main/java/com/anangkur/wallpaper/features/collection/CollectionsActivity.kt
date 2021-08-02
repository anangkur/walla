package com.anangkur.wallpaper.features.collection

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.anangkur.wallpaper.features.collection.databinding.ActivityCollectionBinding
import com.anangkur.wallpaper.presentation.ARGS_ID
import com.anangkur.wallpaper.presentation.ARGS_TITLE
import com.anangkur.wallpaper.presentation.getPreviewDialog
import com.anangkur.wallpaper.R as APP_R

class CollectionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCollectionBinding

    private lateinit var collectionAdapter: CollectionAdapter

    private lateinit var id: String
    private lateinit var title: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCollectionBinding.inflate(layoutInflater)

        setContentView(binding.root)

        getIntentExtra()
        setupToolbar()
        setupRecycler()
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
}
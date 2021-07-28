package com.anangkur.wallpaper.features.preview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.anangkur.wallpaper.data.model.Wallpaper
import com.anangkur.wallpaper.features.preview.databinding.ActivityPreviewBinding
import com.anangkur.wallpaper.presentation.*
import com.anangkur.wallpaper.presentation.features.preview.PreviewViewModel
import com.anangkur.wallpaper.utils.*
import com.google.android.material.bottomsheet.BottomSheetBehavior

class PreviewActivity: AppCompatActivity() {

    private lateinit var binding: ActivityPreviewBinding

    private lateinit var id: String
    private lateinit var title: String
    private lateinit var creator: String
    private lateinit var imageUrl: String
    private var isSaved = false

    private lateinit var previewViewModel: PreviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPreviewBinding.inflate(layoutInflater)

        setContentView(binding.root)

        getArgs()
        setData()
        setupViewModel()
        observeViewModel()
        setAction()
        setBottomSheetExpand()
    }

    private fun setupViewModel() {
        previewViewModel = obtainViewModel(PreviewViewModel::class.java)
    }

    private fun observeViewModel() {
        previewViewModel.apply {
            loading.observe(this@PreviewActivity, Observer {
                setLoadingSave(it)
            })
            error.observe(this@PreviewActivity, Observer {
                showSnackbarShort(it)
            })
            success.observe(this@PreviewActivity, Observer {
                when (it) {
                    PreviewViewModel.Companion.Action.Delete -> {
                        isSaved = !isSaved
                        setData()
                        showSnackbarShort(getString(R.string.message_wallpaper_deleted))
                    }
                    PreviewViewModel.Companion.Action.Insert -> {
                        isSaved = !isSaved
                        setData()
                        showSnackbarShort(getString(R.string.message_wallpaper_saved))
                    }
                }
            })
        }
    }

    private fun getArgs() {
        id = intent.getStringExtra(ARGS_ID).orEmpty()
        title = intent.getStringExtra(ARGS_TITLE).orEmpty()
        creator = intent.getStringExtra(ARGS_CREATOR).orEmpty()
        imageUrl = intent.getStringExtra(ARGS_IMAGE_URL).orEmpty()
        isSaved = intent.getBooleanExtra(ARGS_IS_SAVED, false)
    }

    private fun setData() {
        binding.ivPreview.setImageUrl(imageUrl)
        binding.tvTitle.text = title
        binding.tvCreator.text = creator
        binding.ivSave.setImageDrawable(getIconSave(isSaved))
        binding.tvSave.text = getTextSave(isSaved)
    }

    private fun getIconSave(isSaved: Boolean) = if (isSaved)
        ContextCompat.getDrawable(this, R.drawable.ic_delete)
    else
        ContextCompat.getDrawable(this, R.drawable.ic_save)

    private fun getTextSave(isSaved: Boolean) = if (isSaved) {
        getString(R.string.btn_delete)
    } else {
        getString(R.string.btn_save)
    }

    private fun saveAction(wallpaper: Wallpaper) {
        if (isSaved) {
            previewViewModel.deleteWallpaper(wallpaper.id)
        } else {
            previewViewModel.insertWallpaper(wallpaper)
        }
    }

    private fun setAction() {
        binding.btnClose.setOnClickListener { onBackPressed() }
        binding.btnSet.setOnClickListener { setWallpaper() }
        binding.btnSave.setOnClickListener {
            saveAction(
                Wallpaper(
                    id = id,
                    title = title,
                    imageUrl = imageUrl,
                    creator = creator,
                    isSaved = isSaved
                )
            )
        }
        binding.btnFullscreen.setOnClickListener { onBackPressed() }
    }

    private fun setBottomSheetExpand() {
        val bottomSheetBehaviour = BottomSheetBehavior.from(binding.bottomSheet)
        bottomSheetBehaviour.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun setWallpaper() {
        downloadBitmap(
            onLoading = {
                setLoadingSet(true)
            },
            onFailed = {
                setLoadingSet(false)
                showSnackbarShort(getString(R.string.message_failed_set_wallpaper))
            },
            onResourceReady = {
                setLoadingSet(false)
                setWallpaperDevice(it)
                showSnackbarShort(getString(R.string.message_success_set_wallpaper))
            },
            imageUrl = imageUrl
        )
    }

    private fun setLoadingSet(isLoading: Boolean) {
        binding.flipperSet.displayedChild = if (isLoading) 1 else 0
    }

    private fun setLoadingSave(isLoading: Boolean) {
        binding.flipperSave.displayedChild = if (isLoading) 1 else 0
    }
}
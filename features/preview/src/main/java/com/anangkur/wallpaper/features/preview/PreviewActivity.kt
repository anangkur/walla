package com.anangkur.wallpaper.features.preview

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anangkur.wallpaper.features.preview.databinding.ActivityPreviewBinding
import com.anangkur.wallpaper.utils.*
import com.google.android.material.bottomsheet.BottomSheetBehavior

class PreviewActivity: AppCompatActivity() {

    private lateinit var binding: ActivityPreviewBinding

    private lateinit var title: String
    private lateinit var creator: String
    private lateinit var imageUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPreviewBinding.inflate(layoutInflater)

        setContentView(binding.root)

        getArgs()
        setData()
        setAction()
        setBottomSheetExpand()
    }

    private fun getArgs() {
        title = intent.getStringExtra(ARGS_TITLE).orEmpty()
        creator = intent.getStringExtra(ARGS_CREATOR).orEmpty()
        imageUrl = intent.getStringExtra(ARGS_IMAGE_URL).orEmpty()
    }

    private fun setData() {
        binding.ivPreview.setImageUrl(imageUrl)
        binding.tvTitle.text = title
        binding.tvCreator.text = creator
    }

    private fun setAction() {
        binding.btnClose.setOnClickListener { onBackPressed() }
        binding.btnSet.setOnClickListener { setWallpaper() }
        binding.btnSave.setOnClickListener { Toast.makeText(this, "save", Toast.LENGTH_SHORT).show() }
        binding.btnFullscreen.setOnClickListener { onBackPressed() }
    }

    private fun setBottomSheetExpand() {
        val bottomSheetBehaviour = BottomSheetBehavior.from(binding.bottomSheet)
        bottomSheetBehaviour.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun setWallpaper() {
        downloadBitmap(
            onLoading = {
                binding.flipperSet.displayedChild = 1
            },
            onFailed = {
                binding.flipperSet.displayedChild = 0
                showSnackbarShort(getString(R.string.message_failed_set_wallpaper))
            },
            onResourceReady = {
                setWallpaperDevice(it)
                showSnackbarShort(getString(R.string.message_success_set_wallpaper))
            },
            imageUrl = imageUrl
        )
    }
}
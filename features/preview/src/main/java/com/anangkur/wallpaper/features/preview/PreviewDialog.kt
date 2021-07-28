package com.anangkur.wallpaper.features.preview

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.anangkur.wallpaper.data.model.Wallpaper
import com.anangkur.wallpaper.features.preview.databinding.DialogPreviewBinding
import com.anangkur.wallpaper.presentation.*
import com.anangkur.wallpaper.presentation.features.preview.PreviewViewModel
import com.anangkur.wallpaper.presentation.features.preview.PreviewViewModel.Companion.Action
import com.anangkur.wallpaper.R as APP_R
import com.anangkur.wallpaper.features.preview.R as PREVIEW_R
import com.anangkur.wallpaper.utils.*

class PreviewDialog : DialogFragment() {

    private lateinit var binding: DialogPreviewBinding

    private lateinit var id: String
    private lateinit var title: String
    private lateinit var creator: String
    private lateinit var imageUrl: String
    private var isSaved: Boolean = false

    private lateinit var previewViewModel: PreviewViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return DialogPreviewBinding.inflate(inflater, container, false).also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getArgs()
        setData()
        setupViewModel()
        observeViewModel()
        setOnClickListener()
    }

    override fun onStart() {
        super.onStart()
        setDialogToFullscreen()
        setDialogToTransparent()
    }

    private fun setupViewModel() {
        previewViewModel = obtainViewModel(PreviewViewModel::class.java)
    }

    private fun observeViewModel() {
        previewViewModel.apply {
            loading.observe(viewLifecycleOwner, Observer {
                setLoadingSave(it)
            })
            error.observe(viewLifecycleOwner, Observer {
                requireActivity().showSnackbarShort(it)
            })
            success.observe(viewLifecycleOwner, Observer {
                when (it) {
                    Action.Delete -> {
                        isSaved = !isSaved
                        setData()
                        dialog?.hide()
                        requireActivity().showSnackbarShort(getString(PREVIEW_R.string.message_wallpaper_deleted))
                    }
                    Action.Insert -> {
                        isSaved = !isSaved
                        setData()
                        dialog?.hide()
                        requireActivity().showSnackbarShort(getString(PREVIEW_R.string.message_wallpaper_saved))
                    }
                }
            })
        }
    }

    private fun getArgs() {
        id = arguments?.getString(ARGS_ID).orEmpty()
        title = arguments?.getString(ARGS_TITLE).orEmpty()
        creator = arguments?.getString(ARGS_CREATOR).orEmpty()
        imageUrl = arguments?.getString(ARGS_IMAGE_URL).orEmpty()
        isSaved = arguments?.getBoolean(ARGS_IS_SAVED) ?: false
    }

    private fun setData() {
        binding.tvTitle.text = title
        binding.tvCreator.text = creator
        binding.ivPreview.setImageUrl(imageUrl)
        binding.ivSave.setImageDrawable(getIconSave(isSaved))
        binding.tvSave.text = getTextSave(isSaved)
    }

    private fun getIconSave(isSaved: Boolean) = if (isSaved)
        ContextCompat.getDrawable(requireContext(), PREVIEW_R.drawable.ic_delete)
    else
        ContextCompat.getDrawable(requireContext(), PREVIEW_R.drawable.ic_save)

    private fun getTextSave(isSaved: Boolean) = if (isSaved) {
        getString(PREVIEW_R.string.btn_delete)
    } else {
        getString(PREVIEW_R.string.btn_save)
    }

    private fun setDialogToFullscreen() {
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
        }
    }

    private fun setDialogToTransparent() {
        val dialog: Dialog? = dialog
        if (dialog != null) {
            dialog.window?.setBackgroundDrawableResource(APP_R.color.black_60)
        }
    }

    private fun setOnClickListener() {
        binding.root.setOnClickListener { dialog?.hide() }
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
        binding.btnFullscreen.setOnClickListener {
            requireContext().startPreviewActivity(
                title = title,
                creator = creator,
                imageUrl = imageUrl,
                id = id,
                isSaved = isSaved
            )
        }
    }

    private fun saveAction(wallpaper: Wallpaper) {
        if (isSaved) {
            previewViewModel.deleteWallpaper(wallpaper.id)
        } else {
            previewViewModel.insertWallpaper(wallpaper)
        }
    }

    private fun setWallpaper() {
        requireContext().downloadBitmap(
            onLoading = {
                setLoadingSet(true)
            },
            onFailed = {
                setLoadingSet(false)
                requireActivity().showSnackbarShort(getString(PREVIEW_R.string.message_failed_set_wallpaper))
            },
            onResourceReady = {
                setLoadingSet(false)
                requireContext().setWallpaperDevice(it)
                dialog?.hide()
                requireActivity().showSnackbarShort(getString(PREVIEW_R.string.message_success_set_wallpaper))
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
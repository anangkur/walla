package com.anangkur.wallpaper.features.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.anangkur.wallpaper.data.model.Wallpaper
import com.anangkur.wallpaper.features.saved.databinding.FragmentSavedBinding
import com.anangkur.wallpaper.presentation.getPreviewDialog

class SavedFragment : Fragment() {

    private lateinit var binding: FragmentSavedBinding
    private lateinit var savedAdapter: SavedAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentSavedBinding.inflate(inflater, container, false).also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerSaved()
        setupSwipeRefresh()
        setDataDummy()
    }

    private fun setupRecyclerSaved() {
        savedAdapter = SavedAdapter {
            getPreviewDialog(
                title = it.title,
                creator = it.creator,
                imageUrl = it.imageUrl
            ).show(childFragmentManager, tag)
        }
        binding.recyclerSaved.apply {
            adapter = savedAdapter
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(requireContext(), LinearLayout.VERTICAL, false)
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeSaved.setOnRefreshListener {
            setDataDummy()
            binding.swipeSaved.isRefreshing = false
        }
    }

    private fun setDataDummy() {
        val items = ArrayList<Wallpaper>()
        for (i in 1..10) {
            items.add(
                Wallpaper(
                    id = "",
                    title = "Creation shel",
                    imageUrl = "https://picsum.photos/1080/1920",
                    creator = "by Fallout legacy"
                )
            )
        }
        savedAdapter.setItems(items)
    }
}
package com.anangkur.wallpaper.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.anangkur.wallpaper.features.home.adapter.FavCollectionAdapter
import com.anangkur.wallpaper.features.home.adapter.OtherCollectionAdapter
import com.anangkur.wallpaper.features.home.adapter.SuggestionAdapter
import com.anangkur.wallpaper.features.home.databinding.FragmentHomeBinding
import com.anangkur.wallpaper.features.home.model.Collection
import com.anangkur.wallpaper.features.home.model.WallpaperUiModel
import com.anangkur.wallpaper.presentation.getPreviewDialog

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var suggestionAdapter: SuggestionAdapter
    private lateinit var favCollectionAdapter: FavCollectionAdapter
    private lateinit var otherCollectionAdapter: OtherCollectionAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentHomeBinding.inflate(inflater, container, false).also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSwipeRefresh()
        setupSuggestionAdapter()
        setupFavCollectionAdapter()
        setupOtherCollectionAdapter()

        setDataDummyCollection()
        setDataDummySuggestion()
    }

    private fun setupSwipeRefresh() {
        binding.root.setOnRefreshListener {
            setDataDummyCollection()
            setDataDummySuggestion()
            binding.root.isRefreshing = false
        }
    }

    private fun setupSuggestionAdapter() {
        suggestionAdapter = SuggestionAdapter(
            onClick = {
                getPreviewDialog(
                    title = it.title,
                    creator = it.creator,
                    imageUrl = it.imageUrl
                ).show(childFragmentManager, tag)
            }
        )
        binding.recyclerSuggestion.apply {
            adapter = suggestionAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun setupFavCollectionAdapter() {
        favCollectionAdapter = FavCollectionAdapter()
        binding.recyclerFavoriteCollection.apply {
            adapter = favCollectionAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun setupOtherCollectionAdapter() {
        otherCollectionAdapter = OtherCollectionAdapter()
        binding.recyclerOtherCollection.apply {
            adapter = otherCollectionAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun setDataDummySuggestion() {
        val items = ArrayList<WallpaperUiModel>()
        for (i in 1..10) {
            items.add(
                WallpaperUiModel(
                    id = "",
                    title = "Creation shel",
                    imageUrl = "https://picsum.photos/1080/1920",
                    creator = "by Fallout legacy"
                )
            )
        }
        suggestionAdapter.setItems(items)
    }

    private fun setDataDummyCollection() {
        val items = ArrayList<Collection>()
        val subItems = ArrayList<WallpaperUiModel>()
        for (i in 1..10) {
            subItems.add(
                WallpaperUiModel(
                    id = "",
                    title = "",
                    imageUrl = "https://picsum.photos/1080/1920",
                    creator = "by Fallout legacy"
                )
            )
        }
        for (i in 1..10) {
            items.add(
                Collection(
                    id = "",
                    title = "Amoled Club",
                    description = "A common pitch black wallpaper",
                    imageUrl = "https://picsum.photos/1080/1920",
                    wallpapers = subItems
                )
            )
        }
        favCollectionAdapter.setItems(items)
        otherCollectionAdapter.setItems(items)
    }
}
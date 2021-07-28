package com.anangkur.wallpaper.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.anangkur.wallpaper.features.home.adapter.FavCollectionAdapter
import com.anangkur.wallpaper.features.home.adapter.OtherCollectionAdapter
import com.anangkur.wallpaper.features.home.adapter.SuggestionAdapter
import com.anangkur.wallpaper.features.home.databinding.FragmentHomeBinding
import com.anangkur.wallpaper.presentation.features.home.HomeViewModel
import com.anangkur.wallpaper.presentation.getPreviewDialog
import com.anangkur.wallpaper.presentation.model.BaseResult.Companion.Status
import com.anangkur.wallpaper.utils.obtainViewModel
import com.anangkur.wallpaper.utils.showSnackbarShort
import com.anangkur.wallpaper.R as APP_R

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var suggestionAdapter: SuggestionAdapter
    private lateinit var favCollectionAdapter: FavCollectionAdapter
    private lateinit var otherCollectionAdapter: OtherCollectionAdapter

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentHomeBinding.inflate(inflater, container, false).also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupSwipeRefresh()
        setupSuggestionAdapter()
        setupFavCollectionAdapter()
        setupOtherCollectionAdapter()
        observeViewModel()
    }

    private fun setupViewModel() {
        homeViewModel = obtainViewModel(HomeViewModel::class.java)
    }

    private fun observeViewModel() {
        homeViewModel.fetchWallpaper().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.Error -> requireActivity().showSnackbarShort(it.message.orEmpty().ifEmpty { getString(APP_R.string.error_default) })
                Status.Loading -> binding.root.isRefreshing = it.isLoading ?: false
                Status.Success -> suggestionAdapter.setItems(it.data.orEmpty())
            }
        })
    }

    private fun setupSwipeRefresh() {
        binding.root.setOnRefreshListener {
            binding.root.isRefreshing = false
        }
    }

    private fun setupSuggestionAdapter() {
        suggestionAdapter = SuggestionAdapter(
            onClick = {
                getPreviewDialog(
                    id = it.id,
                    title = it.title,
                    creator = it.creator,
                    imageUrl = it.imageUrl,
                    isSaved = it.isSaved
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
}
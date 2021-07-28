package com.anangkur.wallpaper.features.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.anangkur.wallpaper.features.saved.databinding.FragmentSavedBinding
import com.anangkur.wallpaper.presentation.features.saved.SavedViewModel
import com.anangkur.wallpaper.presentation.getPreviewDialog
import com.anangkur.wallpaper.utils.obtainViewModel
import com.anangkur.wallpaper.utils.showSnackbarShort

class SavedFragment : Fragment() {

    private lateinit var binding: FragmentSavedBinding
    private lateinit var savedAdapter: SavedAdapter

    private lateinit var savedViewModel: SavedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentSavedBinding.inflate(inflater, container, false).also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        observeViewModel()
        setupRecyclerSaved()
        setupSwipeRefresh()
        setupSearch()
        savedViewModel.retrieveWallpaper()
    }

    private fun setupViewModel() {
        savedViewModel = obtainViewModel(SavedViewModel::class.java)
    }

    private fun observeViewModel() {
        savedViewModel.apply {
            wallpapers.observe(viewLifecycleOwner, Observer {
                savedAdapter.setItems(it)
            })
            loading.observe(viewLifecycleOwner, Observer {
                binding.swipeSaved.isRefreshing = it
            })
            error.observe(viewLifecycleOwner, Observer {
                requireActivity().showSnackbarShort(it)
            })
        }
    }

    private fun setupRecyclerSaved() {
        savedAdapter = SavedAdapter {
            getPreviewDialog(
                id = it.id,
                title = it.title,
                creator = it.creator,
                imageUrl = it.imageUrl,
                isSaved = it.isSaved
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
            savedViewModel.retrieveWallpaper()
        }
    }

    private fun setupSearch() {
        binding.searchSaved.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (it.isNotEmpty())
                        savedViewModel.searchWallpaper(it)
                    else
                        savedViewModel.retrieveWallpaper()
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }
}
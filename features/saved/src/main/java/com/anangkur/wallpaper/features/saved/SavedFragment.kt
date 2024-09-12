package com.anangkur.wallpaper.features.saved

import android.content.Intent
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
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.wallpaper.domain.model.Wallpaper
import com.anangkur.wallpaper.features.saved.databinding.FragmentSavedBinding
import com.anangkur.wallpaper.features.saved.di.ViewModelFactory
import com.anangkur.wallpaper.presentation.getPreviewDialog
import com.anangkur.wallpaper.utils.obtainViewModel
import com.anangkur.wallpaper.utils.provideRepository
import com.anangkur.wallpaper.utils.showSnackbarShort
import com.anangkur.wallpaper.R as APP_R

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun setupViewModel() {
        savedViewModel = obtainViewModel(
            SavedViewModel::class.java,
            ViewModelFactory.getInstance(provideRepository()),
        )
    }

    private fun observeViewModel() {
        savedViewModel.apply {
            wallpapers.observe(viewLifecycleOwner, Observer {
                if (it.isEmpty()) setEmptySaved() else setSuccessSaved(it)
            })
            loading.observe(viewLifecycleOwner, Observer {
                if (it) setLoadingSaved()
            })
            error.observe(viewLifecycleOwner, Observer {
                setErrorSaved(it.ifEmpty { getString(APP_R.string.error_default) })
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
                isSaved = it.isSaved,
                thumbnailUrl = it.thumbnailUrl
            ).show(childFragmentManager, tag)
        }
        binding.recyclerSaved.apply {
            adapter = savedAdapter
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeSaved.setOnRefreshListener {
            savedViewModel.retrieveWallpaper()
            binding.swipeSaved.isRefreshing = false
        }
    }

    private fun setupSearch() {
        binding.searchSaved.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.orEmpty().isNotEmpty()) savedViewModel.searchWallpaper(query.orEmpty())
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.orEmpty().isEmpty()) savedViewModel.retrieveWallpaper()
                return true
            }
        })
    }

    private fun setLoadingSaved() {
        binding.flipperSaved.displayedChild = 1
    }

    private fun setErrorSaved(errorMessage: String) {
        binding.flipperSaved.displayedChild = 2
        binding.tvErrorSaved.text = errorMessage
        requireActivity().showSnackbarShort(errorMessage)
        binding.btnRefresh.setOnClickListener { savedViewModel.retrieveWallpaper() }
    }

    private fun setSuccessSaved(wallpapers: List<Wallpaper>) {
        binding.flipperSaved.displayedChild = 0
        savedAdapter.setItems(wallpapers)
    }

    private fun setEmptySaved() {
        binding.flipperSaved.displayedChild = 2
        binding.tvErrorSaved.text = getString(APP_R.string.error_empty)
        binding.ivRefresh.setImageResource(APP_R.drawable.ic_problem)
        binding.btnRefresh.setOnClickListener { savedViewModel.retrieveWallpaper() }
    }
}
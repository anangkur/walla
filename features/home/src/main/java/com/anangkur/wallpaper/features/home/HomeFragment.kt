package com.anangkur.wallpaper.features.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.anangkur.wallpaper.BuildConfig
import com.anangkur.wallpaper.domain.model.Wallpaper
import com.anangkur.wallpaper.features.home.adapter.FavCollectionAdapter
import com.anangkur.wallpaper.features.home.adapter.OtherCollectionAdapter
import com.anangkur.wallpaper.features.home.adapter.SuggestionAdapter
import com.anangkur.wallpaper.features.home.databinding.FragmentHomeBinding
import com.anangkur.wallpaper.features.home.di.ViewModelFactory
import com.anangkur.wallpaper.presentation.getPreviewDialog
import com.anangkur.wallpaper.presentation.startCollectionsActivity
import com.anangkur.wallpaper.utils.obtainViewModel
import com.anangkur.wallpaper.utils.provideRepository
import com.anangkur.wallpaper.utils.showSnackbarShort
import com.anangkur.wallpaper.R as APP_R
import com.anangkur.wallpaper.domain.model.Collection as ModelCollection

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
        homeViewModel.fetchWallpaper(BuildConfig.UNSPLASH_ACCESS_KEY)
        homeViewModel.fetchCollections(BuildConfig.UNSPLASH_ACCESS_KEY)
        homeViewModel.fetchCollections(BuildConfig.UNSPLASH_ACCESS_KEY, 2, 10)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun setupViewModel() {
        homeViewModel = obtainViewModel(
            HomeViewModel::class.java,
            ViewModelFactory.getInstance(provideRepository()),
        )
    }

    private fun observeViewModel() {
        homeViewModel.apply {
            suggestions.observe(viewLifecycleOwner, Observer {
                if (it.isEmpty()) setEmptySuggestion() else setSuccessSuggestion(it)
            })
            errorSuggestions.observe(viewLifecycleOwner, Observer {
                setErrorSuggestion(it.orEmpty().ifEmpty { getString(APP_R.string.error_default) })
            })
            loadingSuggestions.observe(viewLifecycleOwner, Observer {
                if (it) setLoadingSuggestion()
            })
            collections.observe(viewLifecycleOwner, Observer {
                if (it.isEmpty()) setEmptyCollections() else setSuccessCollections(it)
            })
            otherCollections.observe(viewLifecycleOwner, Observer {
                if (it.isEmpty()) setEmptyOtherCollections() else setSuccessOtherCollections(it)
            })
            loadingCollections.observe(viewLifecycleOwner, Observer {
                if (it) setLoadingCollections()
            })
            errorCollections.observe(viewLifecycleOwner, Observer {
                setErrorCollections(it.orEmpty().ifEmpty { getString(APP_R.string.error_default) })
            })
            loadingOtherCollections.observe(viewLifecycleOwner, Observer {
                if (it) setLoadingOtherCollections()
            })
            errorOtherCollections.observe(viewLifecycleOwner, Observer {
                setErrorOtherCollections(it.ifEmpty { getString(APP_R.string.error_default) })
            })
        }
    }

    private fun setupSwipeRefresh() {
        binding.root.setOnRefreshListener {
            homeViewModel.fetchWallpaper(BuildConfig.UNSPLASH_ACCESS_KEY)
            homeViewModel.fetchCollections(BuildConfig.UNSPLASH_ACCESS_KEY)
            homeViewModel.fetchCollections(BuildConfig.UNSPLASH_ACCESS_KEY, 2, 10)
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
                    isSaved = it.isSaved,
                    thumbnailUrl = it.thumbnailUrl
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
        favCollectionAdapter = FavCollectionAdapter { id, title ->
            requireContext().startCollectionsActivity(id, title)
        }
        binding.recyclerFavoriteCollection.apply {
            adapter = favCollectionAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun setupOtherCollectionAdapter() {
        otherCollectionAdapter = OtherCollectionAdapter { id, title ->
            requireContext().startCollectionsActivity(id, title)
        }
        binding.recyclerOtherCollection.apply {
            adapter = otherCollectionAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun setLoadingSuggestion() {
        binding.flipperSuggestion.displayedChild = 1
    }

    private fun setErrorSuggestion(errorMessage: String) {
        binding.flipperSuggestion.displayedChild = 2
        binding.tvError.text = errorMessage
        requireActivity().showSnackbarShort(errorMessage)
        binding.btnRefresh.setOnClickListener { homeViewModel.fetchWallpaper(BuildConfig.UNSPLASH_ACCESS_KEY) }
    }

    private fun setSuccessSuggestion(suggestions: List<Wallpaper>) {
        binding.flipperSuggestion.displayedChild = 0
        suggestionAdapter.setItems(suggestions)
    }

    private fun setEmptySuggestion() {
        binding.flipperSuggestion.displayedChild = 2
        binding.tvError.text = getString(APP_R.string.error_empty)
        binding.ivError.setImageResource(APP_R.drawable.ic_problem)
        binding.btnRefresh.setOnClickListener { homeViewModel.fetchWallpaper(BuildConfig.UNSPLASH_ACCESS_KEY) }
    }

    private fun setLoadingCollections() {
        binding.flipperFavorite.displayedChild = 1
    }

    private fun setErrorCollections(errorMessage: String) {
        binding.flipperFavorite.displayedChild = 2
        binding.tvErrorFav.text = errorMessage
        requireActivity().showSnackbarShort(errorMessage)
        binding.btnRefreshFav.setOnClickListener { homeViewModel.fetchCollections(BuildConfig.UNSPLASH_ACCESS_KEY) }
    }

    private fun setSuccessCollections(collections: List<ModelCollection>) {
        binding.flipperFavorite.displayedChild = 0
        favCollectionAdapter.setItems(collections)
    }

    private fun setEmptyCollections() {
        binding.flipperFavorite.displayedChild = 2
        binding.tvErrorFav.text = getString(APP_R.string.error_empty)
        binding.ivErrorFav.setImageResource(APP_R.drawable.ic_problem)
        binding.btnRefreshFav.setOnClickListener { homeViewModel.fetchCollections(BuildConfig.UNSPLASH_ACCESS_KEY) }
    }

    private fun setLoadingOtherCollections() {
        binding.flipperOtherSuggestions.displayedChild = 1
    }

    private fun setErrorOtherCollections(errorMessage: String) {
        binding.flipperOtherSuggestions.displayedChild = 2
        binding.tvErrorOther.text = errorMessage
        requireActivity().showSnackbarShort(errorMessage)
        binding.btnRefreshOther.setOnClickListener { homeViewModel.fetchCollections(BuildConfig.UNSPLASH_ACCESS_KEY, 2, 10) }
    }

    private fun setSuccessOtherCollections(otherCollections: List<ModelCollection>) {
        binding.flipperOtherSuggestions.displayedChild = 0
        otherCollectionAdapter.setItems(otherCollections)
    }

    private fun setEmptyOtherCollections() {
        binding.flipperOtherSuggestions.displayedChild = 2
        binding.tvErrorOther.text = getString(APP_R.string.error_empty)
        binding.ivErrorOther.setImageResource(APP_R.drawable.ic_problem)
        binding.btnRefreshOther.setOnClickListener { homeViewModel.fetchCollections(BuildConfig.UNSPLASH_ACCESS_KEY, 2, 10) }
    }
}
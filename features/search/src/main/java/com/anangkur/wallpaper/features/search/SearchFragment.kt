package com.anangkur.wallpaper.features.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.anangkur.wallpaper.feature.search.R
import com.anangkur.wallpaper.feature.search.databinding.FragmentSearchBinding
import com.anangkur.wallpaper.presentation.startSearchResultActivity

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private lateinit var colorSelectorAdapter: ColorSelectorAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentSearchBinding.inflate(inflater, container, false).also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupColorSelectorAdapter()
        setupColorSelectorData()

        binding.viewBgSearch.setOnClickListener { requireContext().startSearchResultActivity(null) }
    }

    private fun setupColorSelectorAdapter() {
        colorSelectorAdapter = ColorSelectorAdapter {
            requireContext().startSearchResultActivity(it)
        }
        binding.recyclerColorSelector.apply {
            adapter = colorSelectorAdapter
            itemAnimator = DefaultItemAnimator()
            layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupColorSelectorData() {
        colorSelectorAdapter.setItems(
            listOf(
                R.color.black,
                R.color.white,
                R.color.yellow,
                R.color.orange,
                R.color.red,
                R.color.purple,
                R.color.magenta,
                R.color.green,
                R.color.teal,
                R.color.blue
            )
        )
    }
}
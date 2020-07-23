package com.anangkur.wallpaper.news.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.anangkur.wallpaper.*
import com.anangkur.wallpaper.base.BaseFragment
import com.anangkur.wallpaper.mapper.ArticleMapper
import com.anangkur.wallpaper.model.ArticleIntent
import com.anangkur.wallpaper.news.NewsActivity
import com.anangkur.wallpaper.news.R
import com.anangkur.wallpaper.news.databinding.FragmentHomeBinding
import com.anangkur.wallpaper.presentation.features.news.NewsViewModel
import com.anangkur.wallpaper.presentation.model.BaseResult
import com.anangkur.wallpaper.utils.*
import com.anangkur.wallpaper.R as appR

class HomeFragment: BaseFragment<FragmentHomeBinding, NewsViewModel>(), HomeActionListener {

    override val mViewModel: NewsViewModel
        get() = (requireActivity() as NewsActivity).mViewModel
    override val mToolbar: Toolbar?
        get() = (requireActivity() as NewsActivity).mToolbar

    private lateinit var adapterBreaking: BreakingAdapter
    private lateinit var adapterBusiness: RegularAdapter
    private lateinit var adapterTech: RegularAdapter
    private lateinit var adapterSport: RegularAdapter

    private var mapper = ArticleMapper()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapterBreaking()
        setupAdapterBusiness()
        setupAdapterTech()
        setupAdapterSport()
        observeViewModel()
    }

    private fun observeViewModel(){
        mViewModel.apply {
            topHeadlineNewsLive.observe(viewLifecycleOwner, Observer {
                when (it.status){
                    BaseResult.STATE_LOADING -> {
                        if (it.isLoading!!){
                            mLayout.pbBreaking.visible()
                        }else{
                            mLayout.pbBreaking.gone()
                        }
                    }
                    BaseResult.STATE_ERROR -> {
                        requireActivity().showSnackbarShort(it.message?:"")
                    }
                    BaseResult.STATE_SUCCESS -> {
                        separateMoviesBreaking(mapToView(it.data!!))
                    }
                }
            })
            businessNewsLive.observe(viewLifecycleOwner, Observer {
                when (it.status){
                    BaseResult.STATE_LOADING -> {
                        if (it.isLoading!!){
                            mLayout.pbBusiness.visible()
                        }else{
                            mLayout.pbBusiness.gone()
                        }
                    }
                    BaseResult.STATE_ERROR -> {
                        requireActivity().showSnackbarShort(it.message?:"")
                    }
                    BaseResult.STATE_SUCCESS -> {
                        it.data?.let {listArticle ->
                            adapterBusiness.setRecyclerData(mapToView(listArticle).map { articleView ->
                                mapper.mapToIntent(articleView)
                            })
                        }
                    }
                }
            })
            techNewsLive.observe(viewLifecycleOwner, Observer {
                when (it.status){
                    BaseResult.STATE_LOADING -> {
                        if (it.isLoading!!){
                            mLayout.pbTech.visible()
                        }else{
                            mLayout.pbTech.gone()
                        }
                    }
                    BaseResult.STATE_ERROR -> {
                        requireActivity().showSnackbarShort(it.message?:"")
                    }
                    BaseResult.STATE_SUCCESS -> {
                        it.data?.let {listArticle ->
                            adapterTech.setRecyclerData(mapToView(listArticle).map { articleView ->
                                mapper.mapToIntent(articleView)
                            })
                        }
                    }
                }
            })
            sportNewsLive.observe(viewLifecycleOwner, Observer {
                when (it.status){
                    BaseResult.STATE_LOADING -> {
                        if (it.isLoading!!){
                            mLayout.pbSport.visible()
                        }else{
                            mLayout.pbSport.gone()
                        }
                    }
                    BaseResult.STATE_ERROR -> {
                        requireActivity().showSnackbarShort(it.message?:"")
                    }
                    BaseResult.STATE_SUCCESS -> {
                        it.data?.let {listArticle ->
                            adapterSport.setRecyclerData(mapToView(listArticle).map { articleView ->
                                mapper.mapToIntent(articleView)
                            })
                        }
                    }
                }
            })
            firstTopHeadlineLive.observe(viewLifecycleOwner, Observer {
                setupFirstBreaking(mapper.mapToIntent(it))
            })
            topHeadlineLive.observe(viewLifecycleOwner, Observer {list ->
                adapterBreaking.setRecyclerData(list.map { mapper.mapToIntent(it) })
            })
        }
    }

    private fun setupAdapterBreaking(){
        adapterBreaking = BreakingAdapter(this)
        mLayout.recyclerBreaking.apply {
            adapter = adapterBreaking
            setupRecyclerViewLinear(requireContext(), LinearLayout.VERTICAL)
        }
    }

    private fun setupAdapterBusiness(){
        adapterBusiness = RegularAdapter(this)
        mLayout.recyclerBusiness.apply {
            adapter = adapterBusiness
            setupRecyclerViewLinear(requireContext(), LinearLayout.HORIZONTAL)
        }
    }

    private fun setupAdapterTech(){
        adapterTech = RegularAdapter(this)
        mLayout.recyclerTech.apply {
            adapter = adapterTech
            setupRecyclerViewLinear(requireContext(), LinearLayout.HORIZONTAL)
        }
    }

    private fun setupAdapterSport(){
        adapterSport = RegularAdapter(this)
        mLayout.recyclerSport.apply {
            adapter = adapterSport
            setupRecyclerViewLinear(requireContext(), LinearLayout.HORIZONTAL)
        }
    }

    private fun setupFirstBreaking(data: ArticleIntent){
        mLayout.tvTitleBreaking.text = data.title
        mLayout.tvContentBreaking.text = data.content
        mLayout.ivBreaking.setImageUrl(data.urlToImage?:"")
        mLayout.btnReadMoreBreaking.setOnClickListener { this.onClickItem(data) }
    }

    override fun onClickItem(data: ArticleIntent) {
        mViewModel.selectedNews = mapper.mapFromIntent(data)
        findNavController().navigate(R.id.action_home_fragment_to_detail_fragment)
    }

    override fun setupToolbar(toolbar: Toolbar?) {
        toolbar?.title = getString(appR.string.app_name)
        toolbar?.navigationIcon = null
        toolbar?.setNavigationOnClickListener(null)
    }

    override fun setupView(container: ViewGroup?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(LayoutInflater.from(requireContext()), container, false)
    }
}
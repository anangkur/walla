package com.anangkur.wallpaper.presentation.features.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anangkur.wallpaper.presentation.GetArticles
import com.anangkur.wallpaper.domain.model.Article
import com.anangkur.wallpaper.presentation.mapper.ArticleMapper
import com.anangkur.wallpaper.presentation.model.ArticleView

class NewsViewModel (
    private val getArticles: GetArticles,
    private val mapper: ArticleMapper
): ViewModel(){

    var selectedNews: ArticleView? = null
    var originalNewsUrl = ""

    val topHeadlineNewsLive by lazy { getArticles.getTopHeadlinesNews() }
    val businessNewsLive by lazy { getArticles.getBusinessNews() }
    val techNewsLive by lazy { getArticles.getTechNews() }
    val sportNewsLive by lazy { getArticles.getSportNews() }

    fun mapToView(data: List<Article>): List<ArticleView> {
        return data.map { mapper.mapToView(it) }
    }

    val firstTopHeadlineLive = MutableLiveData<ArticleView>()
    val topHeadlineLive = MutableLiveData<List<ArticleView>>()
    fun separateMoviesBreaking(listNews: List<ArticleView>?){
        if (!listNews.isNullOrEmpty()){
            val tempListData = ArrayList<ArticleView>()
            for (i in listNews.indices){
                if (i == 0){
                    firstTopHeadlineLive.postValue(listNews[i])
                }else if (i <= 5){
                    tempListData.add(listNews[i])
                }
            }
            topHeadlineLive.postValue(tempListData)
        }
    }
}
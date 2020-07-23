package com.anangkur.wallpaper.presentation

import com.anangkur.wallpaper.domain.Const
import com.anangkur.wallpaper.domain.repository.ArticleRepository

open class GetArticles (private val articleRepository: ArticleRepository) {

    companion object{
        private var INSTANCE: GetArticles? = null
        fun getInstance(articleRepository: ArticleRepository) = INSTANCE
            ?: GetArticles(articleRepository)
    }

    fun getTopHeadlinesNews() =
        resultLiveData(
            databaseQuery = { articleRepository.getArticlesLocal(Const.CATEGORY_GENERAL) },
            networkCall = {
                articleRepository.getArticleRemote(
                    Const.API_KEY,
                    Const.COUNTRY_ID,
                    Const.CATEGORY_GENERAL,
                    null,
                    null
                )
            },
            saveCallResult = {
                articleRepository.clearArticle(Const.CATEGORY_GENERAL)
                articleRepository.saveArticles(it.map { article ->
                    article.apply {
                        category = Const.CATEGORY_GENERAL
                    }
                })
            }
        )

    fun getBusinessNews() = resultLiveData(
        databaseQuery = { articleRepository.getArticlesLocal(Const.CATEGORY_BUSSINESS) },
        networkCall = {
            articleRepository.getArticleRemote(
                Const.API_KEY,
                Const.COUNTRY_ID,
                Const.CATEGORY_BUSSINESS,
                null,
                null
            )
        },
        saveCallResult = {
            articleRepository.clearArticle(Const.CATEGORY_BUSSINESS)
            articleRepository.saveArticles(it.map { article ->
                article.apply {
                    category =
                        Const.CATEGORY_BUSSINESS
                }
            })
        }
    )

    fun getTechNews() = resultLiveData(
        databaseQuery = { articleRepository.getArticlesLocal(Const.CATEGORY_TECHNOLOGY) },
        networkCall = {
            articleRepository.getArticleRemote(
                Const.API_KEY,
                Const.COUNTRY_ID,
                Const.CATEGORY_TECHNOLOGY,
                null,
                null
            )
        },
        saveCallResult = {
            articleRepository.clearArticle(Const.CATEGORY_TECHNOLOGY)
            articleRepository.saveArticles(it.map { article ->
                article.apply {
                    category =
                        Const.CATEGORY_TECHNOLOGY
                }
            })
        }
    )

    fun getSportNews() = resultLiveData(
        databaseQuery = { articleRepository.getArticlesLocal(Const.CATEGOGY_SPORT) },
        networkCall = {
            articleRepository.getArticleRemote(
                Const.API_KEY,
                Const.COUNTRY_ID,
                Const.CATEGOGY_SPORT,
                null,
                null
            )
        },
        saveCallResult = {
            articleRepository.clearArticle(Const.CATEGOGY_SPORT)
            articleRepository.saveArticles(it.map { article ->
                article.apply {
                    category = Const.CATEGOGY_SPORT
                }
            })
        }
    )
}
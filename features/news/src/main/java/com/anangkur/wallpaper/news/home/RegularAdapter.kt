package com.anangkur.wallpaper.news.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.anangkur.wallpaper.base.BaseAdapter
import com.anangkur.wallpaper.model.ArticleIntent
import com.anangkur.wallpaper.news.databinding.ItemRegularBinding
import com.anangkur.wallpaper.utils.setImageUrl

class RegularAdapter(private val listener: HomeActionListener): BaseAdapter<ItemRegularBinding, ArticleIntent>() {

    override fun bindView(parent: ViewGroup): ItemRegularBinding {
        return ItemRegularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun bind(data: ArticleIntent, itemView: ItemRegularBinding, position: Int) {
        itemView.ivItemRegular.setImageUrl(data.urlToImage?:"")
        itemView.tvItemRegular.text = data.title
        itemView.root.setOnClickListener { listener.onClickItem(data) }
    }
}
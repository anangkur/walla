package com.anangkur.wallpaper.features.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.wallpaper.data.model.Wallpaper
import com.anangkur.wallpaper.feature.search.databinding.ItemSearchResultBinding
import com.anangkur.wallpaper.utils.setImageUrl

class SearchResultAdapter(private val onClick: (Wallpaper) -> Unit) : RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    private val items = ArrayList<Wallpaper>()

    inner class ViewHolder(private val binding: ItemSearchResultBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Wallpaper) {
            binding.root.setOnClickListener { onClick(item) }
            binding.ivResult.setImageUrl(item.thumbnailUrl)
            binding.tvTitleResult.text = item.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(newItems: List<Wallpaper>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
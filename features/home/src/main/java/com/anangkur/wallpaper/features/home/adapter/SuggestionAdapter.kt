package com.anangkur.wallpaper.features.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.wallpaper.features.home.databinding.ItemSuggestionBinding
import com.anangkur.wallpaper.features.home.model.WallpaperUiModel
import com.anangkur.wallpaper.utils.setImageUrl

class SuggestionAdapter(private val onClick: (WallpaperUiModel) -> Unit) : RecyclerView.Adapter<SuggestionAdapter.ViewHolder>() {

    private val items = ArrayList<WallpaperUiModel>()

    inner class ViewHolder(private val binding: ItemSuggestionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WallpaperUiModel) {
            binding.ivSuggestion.setImageUrl(item.imageUrl)
            binding.root.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSuggestionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<WallpaperUiModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}
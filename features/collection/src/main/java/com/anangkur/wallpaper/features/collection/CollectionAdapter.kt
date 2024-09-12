package com.anangkur.wallpaper.features.collection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.wallpaper.domain.model.Wallpaper
import com.anangkur.wallpaper.features.collection.databinding.ItemWallpaperBinding
import com.anangkur.wallpaper.utils.setImageUrl

class CollectionAdapter(private val onClick: (Wallpaper) -> Unit) : RecyclerView.Adapter<CollectionAdapter.ViewHolder>() {

    private val items = ArrayList<Wallpaper>()

    inner class ViewHolder(private val binding: ItemWallpaperBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Wallpaper) {
            binding.root.setOnClickListener { onClick(item) }
            binding.ivSaved.setImageUrl(item.thumbnailUrl)
            binding.tvTitleSaved.text = item.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemWallpaperBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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
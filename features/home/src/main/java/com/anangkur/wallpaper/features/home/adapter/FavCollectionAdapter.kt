package com.anangkur.wallpaper.features.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.wallpaper.features.home.databinding.ItemFavCollectionBinding
import com.anangkur.wallpaper.data.model.Collection
import com.anangkur.wallpaper.utils.setImageUrl

class FavCollectionAdapter : RecyclerView.Adapter<FavCollectionAdapter.ViewHolder>() {

    private val items = ArrayList<Collection>()

    inner class ViewHolder(private val binding: ItemFavCollectionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Collection) {
            binding.tvTitle.text = item.title
            binding.tvSubTitle.text = item.description
            binding.ivFavCollection1.setImageUrl(item.wallpapers[0])
            binding.ivFavCollection2.setImageUrl(item.wallpapers[1])
            binding.ivFavCollection3.setImageUrl(item.wallpapers[2])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemFavCollectionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<Collection>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}
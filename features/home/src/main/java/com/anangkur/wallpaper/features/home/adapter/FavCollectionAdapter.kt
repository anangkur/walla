package com.anangkur.wallpaper.features.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.wallpaper.features.home.databinding.ItemFavCollectionBinding
import com.anangkur.wallpaper.utils.setImageUrl
import com.anangkur.wallpaper.domain.model.Collection as ModelCollection

class FavCollectionAdapter(
    private val onClick: (id: String, title: String) -> Unit
) : RecyclerView.Adapter<FavCollectionAdapter.ViewHolder>() {

    private val items = ArrayList<ModelCollection>()

    inner class ViewHolder(private val binding: ItemFavCollectionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ModelCollection) {
            binding.tvTitle.text = item.title
            binding.tvSubTitle.text = item.description
            binding.ivFavCollection1.setImageUrl(item.wallpapers[0])
            binding.ivFavCollection2.setImageUrl(item.wallpapers[1])
            binding.ivFavCollection3.setImageUrl(item.wallpapers[2])
            binding.root.setOnClickListener { onClick(item.id, item.title) }
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

    fun setItems(items: List<ModelCollection>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}
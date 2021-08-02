package com.anangkur.wallpaper.features.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.wallpaper.features.home.databinding.ItemOtherCollectionBinding
import com.anangkur.wallpaper.data.model.Collection
import com.anangkur.wallpaper.utils.setImageUrl

class OtherCollectionAdapter(
    private val onClick: (id: String, title: String) -> Unit
) : RecyclerView.Adapter<OtherCollectionAdapter.ViewHolder>() {

    private val items = ArrayList<Collection>()

    inner class ViewHolder(private val binding: ItemOtherCollectionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Collection) {
            binding.tvTitle.text = item.title
            binding.tvSubTitle.text = item.description
            binding.ivFavCollection.setImageUrl(item.wallpapers[0])
            binding.root.setOnClickListener { onClick(item.id, item.title) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemOtherCollectionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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
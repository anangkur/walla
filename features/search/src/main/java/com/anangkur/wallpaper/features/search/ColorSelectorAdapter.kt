package com.anangkur.wallpaper.features.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.wallpaper.feature.search.databinding.ItemColorSelectorBinding

class ColorSelectorAdapter : RecyclerView.Adapter<ColorSelectorAdapter.ViewHolder>() {

    private val items = ArrayList<@ColorRes Int>()

    inner class ViewHolder(private val binding: ItemColorSelectorBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Int) {
            binding.root.setCardBackgroundColor(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemColorSelectorBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<Int>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}
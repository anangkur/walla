package com.anangkur.wallpaper.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<VIEW: ViewBinding, DATA>: RecyclerView.Adapter<BaseAdapter<VIEW, DATA>.BaseViewHolder>() {

    private val listItem: ArrayList<DATA> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(bindView(parent))
    }
    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(listItem[position], position)
    }

    fun setRecyclerData(data: List<DATA>){
        listItem.clear()
        listItem.addAll(data)
        notifyDataSetChanged()
    }

    abstract fun bindView(parent: ViewGroup): VIEW
    abstract fun bind(data: DATA, itemView: VIEW, position: Int)

    inner class BaseViewHolder(private val viewBinding: VIEW): RecyclerView.ViewHolder(viewBinding.root){
        fun bind(data: DATA, position: Int){
            bind(data, viewBinding, position)
        }
    }
}
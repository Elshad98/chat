package com.example.chat.core.platform

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseAdapter<DataT : Any, ViewHolderT : BaseViewHolder<DataT>>(
    diffCallback: DiffUtil.ItemCallback<DataT>
) : ListAdapter<DataT, ViewHolderT>(diffCallback) {

    var onItemClickListener: ((DataT) -> Unit)? = null
    var onItemLongClickListener: ((DataT) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolderT, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(item)
        }
        holder.itemView.setOnLongClickListener {
            onItemLongClickListener?.invoke(item)
            true
        }
    }
}
package com.example.chat.core.platform

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<ItemT : Any>(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(item: ItemT)
}
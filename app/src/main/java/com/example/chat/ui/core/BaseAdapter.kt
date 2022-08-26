package com.example.chat.ui.core

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<ItemT : Any, ViewHolderT : BaseAdapter.BaseViewHolder>(
    diffCallback: DiffUtil.ItemCallback<ItemT>
) : ListAdapter<ItemT, ViewHolderT>(diffCallback) {

    var onClick: OnClick? = null

    override fun onBindViewHolder(holder: ViewHolderT, position: Int) {
        holder.bind(getItem(position))
        holder.onClick = onClick
    }

    fun setOnClick(click: (Any?, View) -> Unit, longClick: (Any?, View) -> Unit = { _, _ -> }) {
        onClick = object : OnClick {

            override fun onClick(item: Any?, view: View) {
                click(item, view)
            }

            override fun onLongClick(item: Any?, view: View) {
                longClick(item, view)
            }
        }
    }

    interface OnClick {

        fun onClick(item: Any?, view: View)

        fun onLongClick(item: Any?, view: View)
    }

    abstract class BaseViewHolder(
        protected val view: View
    ) : RecyclerView.ViewHolder(view) {

        var onClick: OnClick? = null
        var item: Any? = null

        init {
            view.setOnClickListener { view ->
                onClick?.onClick(item, view)
            }
            view.setOnLongClickListener { view ->
                onClick?.onLongClick(item, view)
                true
            }
        }

        protected abstract fun onBind(item: Any)

        fun bind(item: Any) {
            this.item = item
            onBind(item)
        }
    }
}
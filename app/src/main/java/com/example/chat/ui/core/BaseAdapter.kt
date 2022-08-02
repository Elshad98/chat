package com.example.chat.ui.core

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<ViewHolderT : BaseAdapter.BaseViewHolder> : RecyclerView.Adapter<ViewHolderT>() {

    var items: ArrayList<Any> = ArrayList()
    var onClick: OnClick? = null

    abstract val layoutRes: Int

    abstract fun createHolder(parent: ViewGroup): ViewHolderT

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolderT, position: Int) {
        holder.bind(getItem(position))
        holder.onClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderT {
        return createHolder(parent)
    }

    fun getItem(position: Int): Any = items[position]

    fun addItem(newItem: Any) {
        items.add(newItem)
    }

    fun addItems(newItems: List<Any>) {
        items.addAll(newItems)
    }

    fun clear() {
        items.clear()
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
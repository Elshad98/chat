package com.example.chat.ui.core

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.R

abstract class BaseListFragment : BaseFragment() {

    private lateinit var recyclerView: RecyclerView

    protected abstract val viewAdapter: BaseAdapter<*>

    override val layoutId = R.layout.fragment_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
            adapter = viewAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    protected fun setOnItemClickListener(func: (Any?, View) -> Unit) {
        viewAdapter.setOnClick(func)
    }

    protected fun setOnItemLongClickListener(func: (Any?, View) -> Unit) {
        viewAdapter.setOnClick({ _, _ -> }, func)
    }
}
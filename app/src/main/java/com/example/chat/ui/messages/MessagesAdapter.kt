package com.example.chat.ui.messages

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.chat.databinding.ItemMessageMeBinding
import com.example.chat.databinding.ItemMessageOtherBinding
import com.example.chat.domain.messages.MessageEntity
import com.example.chat.extensions.inflater
import com.example.chat.ui.core.BaseAdapter

class MessagesAdapter : ListAdapter<MessageEntity, BaseAdapter.BaseViewHolder>(MessageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseAdapter.BaseViewHolder {
        return if (viewType == 0) {
            MessageMeViewHolder(ItemMessageMeBinding.inflate(parent.inflater, parent, false))
        } else {
            MessageOtherViewHolder(ItemMessageOtherBinding.inflate(parent.inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: BaseAdapter.BaseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        val message = getItem(position)
        return if (message.fromMe) 0 else 1
    }

    class MessageMeViewHolder(
        private val binding: ItemMessageMeBinding
    ) : BaseAdapter.BaseViewHolder(binding.root) {

        init {
            view.setOnClickListener {
                onClick?.onClick(item, it)
            }
        }

        override fun onBind(item: Any) {
            (item as? MessageEntity)?.let {
                binding.message = it
            }
        }
    }

    class MessageOtherViewHolder(
        private val binding: ItemMessageOtherBinding
    ) : BaseAdapter.BaseViewHolder(binding.root) {

        init {
            view.setOnClickListener {
                onClick?.onClick(item, it)
            }
        }

        override fun onBind(item: Any) {
            (item as? MessageEntity)?.let {
                binding.message = it
            }
        }
    }
}
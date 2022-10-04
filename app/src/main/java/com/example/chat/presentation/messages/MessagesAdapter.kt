package com.example.chat.presentation.messages

import android.view.ViewGroup
import com.example.chat.databinding.ItemMessageMeBinding
import com.example.chat.databinding.ItemMessageOtherBinding
import com.example.chat.domain.message.Message
import com.example.chat.core.extension.inflater
import com.example.chat.presentation.core.BaseAdapter

open class MessagesAdapter : BaseAdapter<Message, BaseAdapter.BaseViewHolder>(
    MessageDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (viewType == 0) {
            MessageMeViewHolder(ItemMessageMeBinding.inflate(parent.inflater, parent, false))
        } else {
            MessageOtherViewHolder(ItemMessageOtherBinding.inflate(parent.inflater, parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = getItem(position)
        return if (message.fromMe) 0 else 1
    }

    class MessageMeViewHolder(
        private val binding: ItemMessageMeBinding
    ) : BaseViewHolder(binding.root) {

        init {
            itemView.setOnLongClickListener { view ->
                onClick?.onLongClick(item, view)
                true
            }
            binding.imagePhoto.setOnClickListener { view ->
                onClick?.onClick(item, view)
            }
        }

        override fun onBind(item: Any) {
            (item as? Message)?.let {
                binding.message = it
            }
        }
    }

    class MessageOtherViewHolder(
        private val binding: ItemMessageOtherBinding
    ) : BaseAdapter.BaseViewHolder(binding.root) {

        init {
            itemView.setOnLongClickListener { view ->
                onClick?.onLongClick(item, view)
                true
            }
            binding.imagePhoto.setOnClickListener { view ->
                onClick?.onClick(item, view)
            }
        }

        override fun onBind(item: Any) {
            (item as? Message)?.let {
                binding.message = it
            }
        }
    }
}
package com.example.chat.presentation.messages

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import com.example.chat.R
import com.example.chat.data.cache.AppDatabase
import com.example.chat.data.remote.service.UserService
import com.example.chat.domain.message.Message
import com.example.chat.presentation.App
import com.example.chat.presentation.core.BaseListFragment
import com.example.chat.presentation.viewmodel.MediaViewModel
import com.example.chat.presentation.viewmodel.MessagesViewModel
import kotlinx.android.synthetic.main.fragment_messages.messages_btn_photo
import kotlinx.android.synthetic.main.fragment_messages.messages_btn_send
import kotlinx.android.synthetic.main.fragment_messages.messages_input_text

class MessagesFragment : BaseListFragment() {

    override val viewAdapter = MessagesAdapter()
    override val titleToolbar = R.string.chats
    override val layoutId = R.layout.fragment_messages

    private lateinit var mediaViewModel: MediaViewModel
    private lateinit var messagesViewModel: MessagesViewModel

    private var contactName = ""
    private var contactId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        mediaViewModel = viewModel(MediaViewModel::class.java)
        messagesViewModel = viewModel(MessagesViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mediaViewModel.progressData.observe(viewLifecycleOwner, Observer(::updateProgress))
        mediaViewModel.pickedImageData.observe(viewLifecycleOwner, Observer(::onImagePicked))
        mediaViewModel.cameraFileCreatedData.observe(viewLifecycleOwner, Observer(::onCameraFileCreated))
        messagesViewModel.progressData.observe(viewLifecycleOwner, Observer(::updateProgress))
        mediaViewModel.failureData.observe(viewLifecycleOwner, Observer(::handleFailure))
        messagesViewModel.failureData.observe(viewLifecycleOwner, Observer(::handleFailure))

        base {
            val args = intent.getBundleExtra("args")
            if (args == null) {
                contactId = intent.getLongExtra(UserService.PARAM_CONTACT_ID, 0L)
                contactName = intent.getStringExtra(UserService.PARAM_NAME) ?: ""
            } else {
                contactId = args.getLong(UserService.PARAM_CONTACT_ID)
                contactName = args.getString(UserService.PARAM_NAME, "")
            }
        }

        messages_btn_send.setOnClickListener {
            sendMessage()
        }

        messages_btn_photo.setOnClickListener {
            base {
                navigator.showPickFromDialog(this) { fromCamera ->
                    if (fromCamera) {
                        mediaViewModel.createCameraFile()
                    } else {
                        navigator.showGallery(this)
                    }
                }
            }
        }

        AppDatabase.getInstance(requireContext())
            .messageDao()
            .getLiveMessagesWithContact(contactId)
            .observe(viewLifecycleOwner, Observer(::handleMessages))

        viewAdapter.setOnClick(
            click = { _, view ->
                when (view.id) {
                    R.id.image_photo -> {
                        (view as? ImageView)?.let {
                            navigator.showImageDialog(requireContext(), it.drawable)
                        }
                    }
                }
            },
            longClick = { message, _ ->
                navigator.showDeleteMessageDialog(requireContext()) {
                    (message as? Message)?.let {
                        messagesViewModel.deleteMessage(contactId, it.id)
                    }
                }
            }
        )
    }

    override fun onResume() {
        super.onResume()
        base {
            supportActionBar?.title = contactName
        }

        messagesViewModel.getMessages(contactId)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mediaViewModel.onPickImageResult(requestCode, resultCode, data)
    }

    private fun handleMessages(messages: List<Message>?) {
        if (messages != null && messages.isNotEmpty()) {
            viewAdapter.submitList(messages)
            Handler().postDelayed(
                { recyclerView.smoothScrollToPosition(viewAdapter.itemCount - 1) },
                100
            )
        }
    }

    private fun sendMessage(image: String = "") {
        if (contactId == 0L) {
            return
        }

        val text = messages_input_text.text.toString()

        if (text.isBlank() && image.isBlank()) {
            return
        }

        showProgress()
        messagesViewModel.sendMessage(contactId, text, image)
        messages_input_text.text.clear()
    }

    private fun onCameraFileCreated(uri: Uri?) {
        base {
            if (uri != null) {
                navigator.showCamera(this, uri)
            }
        }
    }

    private fun onImagePicked(pickedImage: MediaViewModel.PickedImage?) {
        if (pickedImage != null) {
            sendMessage(pickedImage.string)
        }
    }
}
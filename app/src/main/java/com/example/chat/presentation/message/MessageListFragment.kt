package com.example.chat.presentation.message

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.result.contract.ActivityResultContracts.TakePicture
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.chat.R
import com.example.chat.core.exception.Failure
import com.example.chat.core.extension.arePermissionsGranted
import com.example.chat.core.extension.isPermissionGranted
import com.example.chat.core.extension.openSystemSettings
import com.example.chat.core.extension.showToast
import com.example.chat.core.extension.supportActionBar
import com.example.chat.core.extension.trimmedText
import com.example.chat.databinding.FragmentMessageListBinding
import com.example.chat.domain.message.Message
import com.example.chat.presentation.extension.installVMBinding
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject
import toothpick.smoothie.viewmodel.closeOnViewModelCleared

class MessageListFragment : Fragment(R.layout.fragment_message_list) {

    private lateinit var adapter: MessageAdapter
    private val args by navArgs<MessageListFragmentArgs>()
    private val viewModel by inject<MessageListViewModel>()
    private val binding by viewBinding(FragmentMessageListBinding::bind)

    private val contentLauncher = registerForActivityResult(GetContent()) { uri ->
        viewModel.sendMessage(args.contactId, uri)
    }

    private val readStoragePermissionLauncher = registerForActivityResult(RequestPermission()) { isGranted ->
        if (isGranted) {
            contentLauncher.launch("image/*")
        } else {
            showWriteStoragePermissionDeniedDialog()
        }
    }

    private val cameraPermissionLauncher = registerForActivityResult(RequestMultiplePermissions()) { grantResults ->
        if (grantResults.values.all { it }) {
            viewModel.createCameraFile()
        } else {
            showCameraPermissionDeniedDialog()
        }
    }

    private val takePictureLauncher = registerForActivityResult(TakePicture()) { isSuccess ->
        if (isSuccess) {
            viewModel.sendImage(args.contactId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KTP.openRootScope()
            .openSubScope(this)
            .installVMBinding<MessageListViewModel>(this)
            .closeOnViewModelCleared(this)
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getMessagesWithContact(args.contactId)
    }

    private fun setupClickListeners() {
        with(binding) {
            buttonSendMessage.setOnClickListener {
                viewModel.sendMessage(args.contactId, inputMessage.trimmedText)
                inputMessage.text?.clear()
            }
            buttonPhoto.setOnClickListener {
                showImagePickDialog()
            }
        }
    }

    private fun showImagePickDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.photo)
            .setItems(R.array.photo_picker_items) { _, which ->
                when (which) {
                    0 -> {
                        if (requireContext().isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            contentLauncher.launch("image/*")
                        } else {
                            readStoragePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        }
                    }
                    1 -> {
                        val permissions = arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                        if (requireContext().arePermissionsGranted(*permissions)) {
                            viewModel.createCameraFile()
                        } else {
                            cameraPermissionLauncher.launch(permissions)
                        }
                    }
                }
            }
            .show()
    }

    private fun showWriteStoragePermissionDeniedDialog() {
        showPermissionDeniedDialog(R.string.write_external_storage_permission_dialog_message)
    }

    private fun showCameraPermissionDeniedDialog() {
        showPermissionDeniedDialog(R.string.camera_permission_dialog_message)
    }

    private fun showPermissionDeniedDialog(@StringRes messageId: Int) {
        AlertDialog.Builder(requireContext())
            .setMessage(messageId)
            .setNegativeButton(R.string.permission_dialog_cancel, null)
            .setPositiveButton(R.string.permission_dialog_ok) { _, _ ->
                requireContext().openSystemSettings()
            }
            .show()
    }

    private fun setupRecyclerView() {
        adapter = MessageAdapter(
            object : OnMessageClickListener {

                override fun onMessageClick(message: Message) {
                    launchImageViewerFragment(message)
                }

                override fun onMessageLongClick(message: Message) {
                    showDeleteMessageDialog(message)
                }
            }
        )
        binding.recyclerView.apply {
            this.adapter = this@MessageListFragment.adapter
            layoutManager = LinearLayoutManager(context).apply {
                stackFromEnd = true
            }
        }
    }

    private fun setupToolbar() {
        supportActionBar?.run {
            show()
            title = args.contactName
            setDisplayShowTitleEnabled(true)
        }
    }

    private fun observeViewModel() {
        viewModel.failure.observe(viewLifecycleOwner, ::handleFailure)
        viewModel.cameraFile.observe(viewLifecycleOwner, takePictureLauncher::launch)
        viewModel.getMessages(args.contactId).observe(viewLifecycleOwner, ::handleMessageList)
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.NetworkConnectionError -> R.string.error_network
            is Failure.FilePickError -> R.string.error_picking_file
            else -> R.string.error_server
        }.let(::showToast)
    }

    private fun handleMessageList(messages: List<Message>) {
        adapter.submitList(messages) {
            binding.recyclerView.scrollToPosition(messages.lastIndex)
        }
    }

    private fun showDeleteMessageDialog(message: Message) {
        AlertDialog.Builder(requireContext())
            .setTitle(requireContext().getString(R.string.delete_message_title))
            .setMessage(requireContext().getString(R.string.delete_message_text))
            .setPositiveButton(R.string.dialog_yes) { _, _ ->
                viewModel.deleteMessage(message.id, args.contactId)
            }
            .setNegativeButton(R.string.dialog_no, null)
            .show()
    }

    private fun launchImageViewerFragment(message: Message) {
        findNavController()
            .navigate(MessageListFragmentDirections.actionMessageListFragmentToImageViewerFragment(message.message))
    }
}
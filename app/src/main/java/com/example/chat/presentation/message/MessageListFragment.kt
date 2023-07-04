package com.example.chat.presentation.message

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
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
import com.example.chat.di.ViewModelFactory
import com.example.chat.domain.message.Message
import com.example.chat.presentation.App
import javax.inject.Inject

class MessageListFragment : Fragment(R.layout.fragment_message_list) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var adapter: MessageAdapter
    private val args by navArgs<MessageListFragmentArgs>()
    private val binding by viewBinding(FragmentMessageListBinding::bind)
    private val viewModel: MessageListViewModel by viewModels(factoryProducer = { viewModelFactory })

    private val contentLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        viewModel.sendMessage(args.contactId, uri)
    }

    private val readStoragePermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            contentLauncher.launch("image/*")
        } else {
            showWriteStoragePermissionDeniedDialog()
        }
    }

    private val cameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { grantResults ->
        if (grantResults.values.all { it }) {
            viewModel.createCameraFile()
        } else {
            showCameraPermissionDeniedDialog()
        }
    }

    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            viewModel.sendImage(args.contactId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
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
        adapter = MessageAdapter()
        binding.recyclerView.adapter = adapter
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
}
package com.example.chat.presentation.imageviewer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.chat.R
import com.example.chat.core.extension.load
import com.example.chat.databinding.FragmentImageViewerBinding

class ImageViewerFragment : Fragment(R.layout.fragment_image_viewer) {

    private val args by navArgs<ImageViewerFragmentArgs>()
    private val binding by viewBinding(FragmentImageViewerBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imagePhoto.load(args.image, R.drawable.picture_placeholder)
    }
}
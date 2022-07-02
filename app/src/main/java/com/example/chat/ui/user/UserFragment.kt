package com.example.chat.ui.user

import android.os.Bundle
import android.view.View
import com.example.chat.R
import com.example.chat.extensions.gone
import com.example.chat.remote.service.ApiService
import com.example.chat.ui.core.BaseFragment
import com.example.chat.ui.core.GlideHelper
import kotlinx.android.synthetic.main.fragment_user.user_img_photo
import kotlinx.android.synthetic.main.fragment_user.user_label_email
import kotlinx.android.synthetic.main.fragment_user.user_label_hint_status
import kotlinx.android.synthetic.main.fragment_user.user_label_name
import kotlinx.android.synthetic.main.fragment_user.user_label_status

class UserFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_user
    override val titleToolbar: Int = R.string.screen_user

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        base {
            val args = intent.getBundleExtra("args")
            if (args != null) {
                val image = args.getString(ApiService.PARAM_IMAGE)
                val name = args.getString(ApiService.PARAM_NAME)
                val email = args.getString(ApiService.PARAM_EMAIL)
                val status = args.getString(ApiService.PARAM_STATUS)

                GlideHelper.loadImage(
                    requireContext(),
                    image,
                    user_img_photo,
                    R.drawable.ic_account_circle
                )

                user_label_name.text = name
                user_label_email.text = email
                user_label_status.text = status

                if (status.isNullOrEmpty()) {
                    user_label_status.gone()
                    user_label_hint_status.gone()
                }
            }
        }
    }
}
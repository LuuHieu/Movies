package com.amaris.movies.base

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.amaris.movies.R
import com.bumptech.glide.Glide

class LoadingDialog {
    private var dialog: Dialog? = null

    fun show(context: Context) {
//        if (dialog == null) {
//            val inflater = LayoutInflater.from(context)
//            val binding = DialogLoadingBinding.inflate(inflater)
//            Glide.with(binding.root)
//                .asGif()
//                .load(R.raw.loading)
//                .into(binding.image)
//            dialog = Dialog(context)
//            dialog?.apply {
//                setCancelable(false)
//                setContentView(binding.root)
//                window?.apply {
//                    setGravity(Gravity.CENTER)
//                    decorView.setBackgroundResource(android.R.color.transparent)
//                    setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//                    setDimAmount(0.5F)
//                }
//                create()
//            }
//        }
        dialog?.apply {
            if (!isShowing) show()
        }
    }

    fun dismiss() {
        dialog?.apply {
            if (isShowing) dismiss()
        }
    }
}
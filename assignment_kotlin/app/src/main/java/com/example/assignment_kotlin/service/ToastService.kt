package com.example.assignment_kotlin.service

import android.content.Context
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.assignment_kotlin.R
import com.example.assignment_kotlin.utilities.ToastType

class ToastService {
    companion object {
        val instance = ToastService()
    }

    fun showToast(window: Window, context: Context, toastType: ToastType, message: String) {
        val toastView = window.layoutInflater.inflate(
            R.layout.layout_toast,
            window.findViewById(R.id.toastContainer)
        )

        val toastContainer: LinearLayout = toastView.findViewById(R.id.toastContainer)
        val toastIcon: ImageView = toastView.findViewById(R.id.toastIcon)
        val toastText: TextView = toastView.findViewById(R.id.toastText)

        when (toastType) {
            ToastType.SUCCESS -> {
                toastContainer.setBackgroundResource(R.drawable.toast_success_background)
                toastIcon.setImageResource(R.drawable.ic_success)
                toastText.text = message
            }

            ToastType.ERROR -> {
                toastContainer.setBackgroundResource(R.drawable.toast_error_background)
                toastIcon.setImageResource(R.drawable.ic_error)
                toastText.text = message
            }

            ToastType.INFORMATION -> {
                toastContainer.setBackgroundResource(R.drawable.toast_information_background)
                toastIcon.setImageResource(R.drawable.ic_info)
                toastText.text = message
            }

            ToastType.WARNING -> {
                toastContainer.setBackgroundResource(R.drawable.toast_warning_background)
                toastIcon.setImageResource(R.drawable.ic_warning)
                toastText.text = message
            }
        }

        with(Toast(context)) {
            duration = Toast.LENGTH_SHORT
            view = toastView
            show()
        }
    }
}
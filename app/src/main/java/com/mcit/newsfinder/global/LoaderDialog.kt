package com.mcit.newsfinder.global

import android.app.Dialog
import android.content.Context
import com.mcit.newsfinder.R


class LoaderDialog private constructor(context: Context) :
    Dialog(context, R.style.TransparentDialog) {
    init {
        setContentView(R.layout.view_loader)
        setCancelable(false)
    }


    companion object {
        private var loaderDialogInstance: LoaderDialog? = null
        private var originalContext: Context? = null
        @JvmStatic
        fun getInstance(context: Context): LoaderDialog {
            if (loaderDialogInstance == null || originalContext != context) {
                originalContext = context
                loaderDialogInstance = LoaderDialog(context)
            }
            return loaderDialogInstance as LoaderDialog
        }
    }

    fun showProgress() {
        loaderDialogInstance?.show()
    }

    fun hideProgress() {
        loaderDialogInstance?.dismiss()
    }
}
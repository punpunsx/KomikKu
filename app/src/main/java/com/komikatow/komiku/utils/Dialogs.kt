package com.komikatow.komiku.utils

import android.app.ProgressDialog
import android.content.Context
import com.c.progress_dialog.BlackProgressDialog

private lateinit var dialogProgrees:ProgressDialog
lateinit var dialogProgressCustum:BlackProgressDialog

fun setDialogLoading(context: Context, title: String, messege: String, canclelable: Boolean){

    dialogProgrees = ProgressDialog(context)
    dialogProgrees.setTitle(title)
    dialogProgrees.setMessage(messege)
    dialogProgrees.setCancelable(canclelable)

}

fun showDialogLoading() = dialogProgrees.show()
fun dismissDialogLoading() = dialogProgrees.dismiss()


fun setDialogLoadingCustum(context: Context, msg:String){

    dialogProgressCustum = BlackProgressDialog(context, msg)
    dialogProgressCustum.setMsg(msg)

}
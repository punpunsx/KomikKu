package com.komikatow.komiku.utils

import android.app.ProgressDialog
import android.content.Context
import com.c.progress_dialog.BlackProgressDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.komikatow.komiku.R

private lateinit var dialogProgrees:ProgressDialog
private lateinit var alertDialog:MaterialAlertDialogBuilder
lateinit var dialogProgressCustum:BlackProgressDialog

interface OnDialogListener{
    fun onOkeButton()
    fun onCencleButton()

}
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

fun setAlertDialog(context: Context, title: String, msg: String, canclelable: Boolean, listener: OnDialogListener) {
    alertDialog = MaterialAlertDialogBuilder(context)
    alertDialog.setTitle(title)
    alertDialog.setMessage(msg)
    alertDialog.setIcon(R.drawable.baseline_info_24)
    alertDialog.setCancelable(canclelable)
    alertDialog.setPositiveButton("oke") { dialog, _ ->
        listener.onOkeButton()
        dialog!!.dismiss()
    }
    alertDialog.setNegativeButton("Batal") { dialog2, _ ->
            listener.onCencleButton()
            dialog2!!.dismiss()
    }
}

fun showAlertDialog() = alertDialog.show()

package com.komikatow.komiku.utils

import android.app.ProgressDialog
import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.c.progress_dialog.BlackProgressDialog

lateinit var dialogProgrees:ProgressDialog
lateinit var dialogProgressCustum:BlackProgressDialog

interface dialogClick{
    fun onOkeClick()
    fun onCancleClick(){}

}
fun setAlertDialog(context:Context, title:String, messege:String, canclelable:Boolean, dialogClick: dialogClick ){

    val dialog = MaterialAlertDialogBuilder(context)
    dialog.setTitle(title)
    dialog.setMessage(messege)
    dialog.setCancelable(canclelable)
    dialog.setPositiveButton("Oke") { dialogPos, _ ->
        dialogClick.onOkeClick()
        dialogPos.dismiss()
    }
    dialog.setNegativeButton("Batal") { dialogCencle, _ ->
        dialogClick.onCancleClick()
        dialogCencle.dismiss()
    }
    dialog.show()
}

fun setDialogLoading(context: Context, title: String, messege: String, canclelable: Boolean){

    dialogProgrees = ProgressDialog(context)
    dialogProgrees.setTitle(title)
    dialogProgrees.setMessage(messege)
    dialogProgrees.setCancelable(canclelable)

}

fun setDialogLoadingCustum(context: Context, msg:String){

    dialogProgressCustum = BlackProgressDialog(context, msg)
    dialogProgressCustum.setMsg(msg)

}
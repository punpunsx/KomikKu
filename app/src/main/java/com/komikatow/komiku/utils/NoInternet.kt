package com.komikatow.komiku.utils

import android.app.Activity
import androidx.lifecycle.Lifecycle
import org.imaginativeworld.oopsnointernet.dialogs.signal.NoInternetDialogSignal

class NoInternet {

    companion object{
        fun checkInternet(lifecycle: Lifecycle, activity: Activity){

            NoInternetDialogSignal.Builder(activity, lifecycle).apply {
                dialogProperties.apply {


                    cancelable = false
                    noInternetConnectionTitle = "No Internet"
                    noInternetConnectionMessage =
                            "Check your Internet connection and try again."
                    showInternetOnButtons = true
                    pleaseTurnOnText = "Please turn on"
                    wifiOnButtonText = "Wifi"
                    mobileDataOnButtonText = "Mobile data"

                    onAirplaneModeTitle = "No Internet"
                    onAirplaneModeMessage = "You have turned on the airplane mode."
                    pleaseTurnOffText = "Please turn off"
                    airplaneModeOffButtonText = "Airplane mode"
                    showAirplaneModeOffButtons = true
                }
            }.build()
        }
    }
}
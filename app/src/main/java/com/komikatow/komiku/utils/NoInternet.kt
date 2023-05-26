package com.komikatow.komiku.utils

import android.app.Activity
import androidx.lifecycle.Lifecycle
import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback
import org.imaginativeworld.oopsnointernet.dialogs.pendulum.NoInternetDialogPendulum

class NoInternet {

    companion object{
        fun checkInternet(lifecycle: Lifecycle, activity: Activity){

            NoInternetDialogPendulum.Builder(activity, lifecycle).apply {
                dialogProperties.apply {
                    connectionCallback = object : ConnectionCallback { // Optional
                        override fun hasActiveConnection(hasActiveConnection: Boolean) {
                            // ...
                        }
                    }

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
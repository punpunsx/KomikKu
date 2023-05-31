package com.komikatow.komiku.ui.activityes.backup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.androidnetworking.error.ANError
import com.komikatow.komiku.databinding.ActivityDetailBinding
import com.komikatow.komiku.ui.activityes.BaseActivity
import com.komikatow.komiku.utils.Endpoints
import com.komikatow.komiku.utils.Networking
import com.komikatow.komiku.utils.dialogProgressCustum
import org.json.JSONObject

class DetailBackupActivity : BaseActivity <ActivityDetailBinding>() {

    override fun createBinding(layoutInflater: LayoutInflater): ActivityDetailBinding {
        return ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getBackupDetailComic()
    }

    private fun getBackupDetailComic() {

        Networking.HttpsRequest(Endpoints.KOMIK_DETAIL_BACKUP+intent.getStringExtra("endpoint"), this, object : Networking.Response{
            override fun onHttpsResponse(jsonObject: JSONObject?) {

            }

            override fun onHttpsError(anError: ANError?) {
                Toast.makeText(this@DetailBackupActivity, "Error saat mencoba mengambil data disebabkan : " + anError!!.errorDetail, Toast.LENGTH_SHORT).show()
                dialogProgressCustum.dismiss()

                binding.parentLottie.visibility = View.VISIBLE
                binding.nested.visibility = View.GONE
            }
        })
    }
}
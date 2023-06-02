package com.komikatow.komiku.ui.activityes.backup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.error.ANError
import com.blogspot.atifsoftwares.animatoolib.Animatoo.animateSwipeRight
import com.komikatow.komiku.adapter.AdapterChapterDetail
import com.komikatow.komiku.databinding.ActivityBacaBackupBinding
import com.komikatow.komiku.model.ModelChapterDetail
import com.komikatow.komiku.ui.activityes.BaseActivity
import com.komikatow.komiku.utils.Endpoints
import com.komikatow.komiku.utils.Networking
import org.json.JSONObject

class BacaBackupActivity : BaseActivity<ActivityBacaBackupBinding>() {

    private var transitionStatus = false
    private var modeBaca = false;
    private val list:MutableList<ModelChapterDetail <String> > = ArrayList()
    override fun createBinding(layoutInflater: LayoutInflater): ActivityBacaBackupBinding =  ActivityBacaBackupBinding.inflate(layoutInflater)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        transitionStatus = sharedPreferences.getBoolean("animasiTransisi", false)
        modeBaca = sharedPreferences.getBoolean("mode", false)

        binding.toolbar.setNavigationOnClickListener {

            if (transitionStatus) {
                finish()
                animateSwipeRight(this)

            } else {
                finish()
            }

        }
        getDetailChapterBackup()
    }

    private fun getDetailChapterBackup(){

        Networking.HttpsRequest(Endpoints.KOMIK_DETAIL_CHAPTER_BACKUP+intent.getStringExtra("endpoint"), this, object :Networking.Response{
            override fun onHttpsResponse(jsonObject: JSONObject) {

                val data = jsonObject.optJSONObject("data")
                binding.toolbar.title = data.optString("title")
                binding.toolbar.subtitle = intent.getStringExtra("numberCh")

                val image = data.optJSONArray("image")
                for (i in 0 until image.length()){

                    val modelChapterDetail = ModelChapterDetail <String> ()
                    modelChapterDetail.image = image.optString(i)
                    list.add(modelChapterDetail)
                }
                adapterChapter(list)
            }

            override fun onHttpsError(anError: ANError?) {
                binding.parentLottie.visibility = View.VISIBLE

            }
        })
    }

    private fun adapterChapter(list: MutableList<ModelChapterDetail<String>>) {

        val adapter = AdapterChapterDetail(list, this)
        binding.rvChDetail.adapter = adapter

        if (modeBaca) {
            binding.rvChDetail.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        } else {
            binding.rvChDetail.layoutManager = LinearLayoutManager(this)
        }

    }



    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        if (transitionStatus) {
            animateSwipeRight(this)
        }
    }
}
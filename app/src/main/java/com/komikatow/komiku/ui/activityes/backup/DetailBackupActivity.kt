package com.komikatow.komiku.ui.activityes.backup

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.error.ANError
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import com.komikatow.komiku.R
import com.komikatow.komiku.adapter.AdapterChapter
import com.komikatow.komiku.adapter.AdapterGenre
import com.komikatow.komiku.databinding.ActivityDetailBinding
import com.komikatow.komiku.model.ModelGenre
import com.komikatow.komiku.room.enity.ModelChapter
import com.komikatow.komiku.ui.activityes.BaseActivity
import com.komikatow.komiku.utils.Endpoints
import com.komikatow.komiku.utils.Networking
import com.komikatow.komiku.utils.dialogProgressCustum
import org.json.JSONObject

class DetailBackupActivity : BaseActivity <ActivityDetailBinding>() {

    private val listGenre:MutableList<ModelGenre <String> > = ArrayList()
    private val listChapter:MutableList<ModelChapter > = ArrayList()

    private var transitionStatus = false
    private var animasiGambar = false
    private var getBahasa = false
    private var modeCh = false

    override fun createBinding(layoutInflater: LayoutInflater): ActivityDetailBinding {
        return ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        transitionStatus = sharedPreferences.getBoolean("animasiTransisi", false)
        animasiGambar = sharedPreferences.getBoolean("animasiGambar", true)
        getBahasa = sharedPreferences.getBoolean("bahasa", true)
        modeCh = sharedPreferences.getBoolean("modeCh", true)

        getBackupDetailComic()
    }

    private fun getBackupDetailComic() {

        Networking.HttpsRequest(Endpoints.KOMIK_DETAIL_BACKUP+intent.getStringExtra("endpoint"), this, object : Networking.Response{
            @SuppressLint("SetTextI18n")
            override fun onHttpsResponse(jsonObject: JSONObject) {

                val data = jsonObject.optJSONObject("data")
                Glide.with(this@DetailBackupActivity)
                    .load(data.optString("thumbnail"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.color.PlaceHolderImage)
                    .override(Target.SIZE_ORIGINAL)
                    .error(R.color.ErrorLoadImage)
                    .into(binding.detailImage)

                binding.colapse.title = data.optString("title")
                binding.tvAuthor.text = "Author : "+data.optString("author")
                binding.tvIlustrator.text = "Min Usia : "+data.optString("rating")
                binding.tvStatus.text = "Status : "+ data.optString("status")
                binding.tvType.visibility = View.VISIBLE
                binding.tvType.text = "Type : "+ data.optString("type")
                binding.tvRating.visibility = View.GONE

                getResponseBackupGenres(data)
                getResponseBackupChapter(data)

            }

            override fun onHttpsError(anError: ANError) {
                dialogProgressCustum.dismiss()

                val rootView:View = findViewById(android.R.id.content)
                val snackbar = Snackbar.make(applicationContext,rootView, "Terjadi error saat menampilkan data ", Snackbar.LENGTH_SHORT)
                snackbar.setAction("Tutup") { snackbar.dismiss() }
                snackbar.show()
                binding.parentLottie.visibility = View.VISIBLE
                binding.nested.visibility = View.GONE
            }
        })
    }
    private fun getResponseBackupGenres(jsonObject: JSONObject){

        val jsonArray  = jsonObject.optJSONArray("genre")
        if (jsonArray != null) {
            for (i in 0 until jsonArray.length()){

                val modelGenre = ModelGenre <String> ()
                modelGenre.genre = jsonArray.optString(i)
                listGenre.add(modelGenre)

            }
        }
        adapterGenre(listGenre)
    }

    private fun adapterGenre(listGenre: MutableList<ModelGenre<String>>) {

        val adapter = AdapterGenre(this, listGenre)
        binding.rvGenre.layoutManager = GridLayoutManager(this, 4)
        binding.rvGenre.adapter = adapter

    }

    private fun getResponseBackupChapter(jsonObject: JSONObject){

        val jsonArray = jsonObject.optJSONArray("chapter_list")

        if (jsonArray != null) {
            for (i in 0 until jsonArray.length()){
                val data = jsonArray.optJSONObject(i)

                val modelChapterBackup = ModelChapter()
                modelChapterBackup.endPointCh = data.optString("endpoint")
                modelChapterBackup.nemeCh = data.optString("name")
                listChapter.add(modelChapterBackup)

            }
        }
        adapterChapter(listChapter)
    }

    @SuppressLint("SetTextI18n")
    private fun adapterChapter(listChapter: MutableList<ModelChapter>) {
        val adapter = AdapterChapter(this, this, listChapter) { pos ->
            val intent = Intent(applicationContext, BacaBackupActivity::class.java)
            intent.putExtra("endpoint", listChapter[pos].endPointCh)
            intent.putExtra("numberCh", listChapter[pos].nemeCh)

            if (transitionStatus) {
                startActivity(intent)
                Animatoo.animateZoom(this)
                finish()

            } else {
                startActivity(intent)
                finish()

            }
        }
        binding.rvChapter.adapter = adapter
        if (getBahasa) {
            binding.tvTotalCh.text = "Total Chapters : " + adapter.itemCount
        } else {
            binding.tvTotalCh.text = "Jumlah Chapter : " + adapter.itemCount
        }


        if (modeCh) {
            binding.rvChapter.layoutManager = GridLayoutManager(applicationContext, 4)
        } else {
            binding.rvChapter.layoutManager = LinearLayoutManager(applicationContext)
        }
    }
}
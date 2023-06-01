package com.komikatow.komiku.ui.fragments.backup

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.error.ANError
import com.blogspot.atifsoftwares.animatoolib.Animatoo.animateZoom
import com.komikatow.komiku.adapter.AdapterKomik
import com.komikatow.komiku.adapter.AdapterKomikJepang
import com.komikatow.komiku.databinding.FragmentHomeBinding
import com.komikatow.komiku.model.ModelBaseKomik
import com.komikatow.komiku.ui.activityes.DetailActivity
import com.komikatow.komiku.ui.activityes.SearchActivity
import com.komikatow.komiku.ui.fragments.BaseFragment
import com.komikatow.komiku.utils.Endpoints
import com.komikatow.komiku.utils.ItemRecyclerClick
import com.komikatow.komiku.utils.Networking
import com.komikatow.komiku.utils.dismissDialogLoading
import com.komikatow.komiku.utils.setDialogLoading
import com.komikatow.komiku.utils.showDialogLoading
import org.json.JSONObject

//Maaf kalo di kelas ini banyak code duplication.
class BackUpHomeFragment : BaseFragment<FragmentHomeBinding> () {

    private val listKomikJepangBackup:MutableList< ModelBaseKomik <String> > = ArrayList()
    private val listKomikManhwaBackup:MutableList< ModelBaseKomik <String> > = ArrayList()
    private val listKomikManhuaBackup:MutableList< ModelBaseKomik <String> > = ArrayList()
    private val listKomikTerbaruBackup:MutableList< ModelBaseKomik <String> > = ArrayList()
    private val listKomikPopulerBackup:MutableList< ModelBaseKomik <String> > = ArrayList()

    private var sharedPreferences: SharedPreferences? = null

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getBackupManga()

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        binding.btnSarch.setOnClickListener { v ->
            startActivity(Intent(context, SearchActivity::class.java))
            if (sharedPreferences!!.getBoolean("animasiTransisi", false)) {
                animateZoom(requireContext())
            }
        }

        if (sharedPreferences!!.getBoolean("bahasa", true)) {
            binding.txtKomikJepangId.text = "Japanese comics"
            binding.txtGenreId.text = "List Genre"
            binding.txtReleaseId.text = "Latest comic updates"
            binding.txtKomikKoreaId.text = "Korean comics"
            binding.txtKomikChinaId.text = "Chinese comics"
            setDialogLoading(requireContext(), "Loading...", "Please Wait", true)
            showDialogLoading()

        } else {
            setDialogLoading(requireContext(), "Loading...", "Mohon tunggu sebentar", true)
            showDialogLoading()
        }

    }

    private fun getBackupManga(){

        Networking.HttpsRequest(Endpoints.KOMIK_TYPE_MANGA_BACKUP, activity,object : Networking.Response{
            override fun onHttpsResponse(jsonObject: JSONObject) {

                val jsonArray = jsonObject.optJSONArray("data")

                if (jsonArray != null) {
                    for (i in 0 until 30){

                        val data = jsonArray.optJSONObject(i)
                        val modelBaseKomik = ModelBaseKomik<String>()

                        modelBaseKomik.title = data.optString("title")
                        modelBaseKomik.thumbnail = data.optString("image")
                        modelBaseKomik.endPoint = data.optString("endpoint")
                        modelBaseKomik.type = "Manga"
                        listKomikJepangBackup.add(modelBaseKomik)
                    }
                    adapterManga(listKomikJepangBackup)
                }
            }

            override fun onHttpsError(anError: ANError?) {
                //dismissDialogLoading()
                Toast.makeText(context, "Error on : " + anError!!.errorDetail, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun adapterManga(listKomikJepangBackup: MutableList<ModelBaseKomik<String>>) {

        val adapterKomikJepang = AdapterKomikJepang(context, listKomikJepangBackup, object : ItemRecyclerClick{
            override fun onClickListener(pos: Int) {

                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("endpoint", listKomikJepangBackup[pos].endPoint)
                intent.putExtra("type", listKomikJepangBackup[pos].type)


                val transitionStatus = sharedPreferences!!.getBoolean("animasiTransisi", false)

                if (transitionStatus) {
                    startActivity(intent)
                    animateZoom(requireContext())
                } else {
                    startActivity(intent)
                }
            }
        })
        binding.rvKomikJepangSlider.adapter = adapterKomikJepang
        getPopulerManga()
    }

    private fun getPopulerManga(){

        Networking.HttpsRequest(Endpoints.KOMIK_POPULER_BACKUP, activity,object : Networking.Response{
            override fun onHttpsResponse(jsonObject: JSONObject) {

                val jsonArray = jsonObject.optJSONArray("data")

                if (jsonArray != null) {
                    for (i in 0 until jsonArray.length()){

                        val data = jsonArray.optJSONObject(i)
                        val modelBaseKomik = ModelBaseKomik<String>()

                        modelBaseKomik.title = data.optString("title")
                        modelBaseKomik.thumbnail = data.optString("image")
                        modelBaseKomik.endPoint = data.optString("endpoint")
                        modelBaseKomik.type = data.optString("type")
                        listKomikPopulerBackup.add(modelBaseKomik)
                    }
                    adapterPopuler(listKomikPopulerBackup)
                }
            }

            override fun onHttpsError(anError: ANError?) {
                dismissDialogLoading()
                Toast.makeText(context, "Error on : " + anError!!.errorDetail, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun adapterPopuler(listKomikPopulerBackup: MutableList<ModelBaseKomik<String>>) {

        val adapter = AdapterKomik(context, listKomikPopulerBackup)
        binding.rvListGenre.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvListGenre.adapter = adapter

        getNewRelease()
    }

    private fun getNewRelease(){

        Networking.HttpsRequest(Endpoints.KOMIK_TERBARU_BACKUP, activity,object : Networking.Response{
            override fun onHttpsResponse(jsonObject: JSONObject) {

                val jsonArray = jsonObject.optJSONArray("data")

                if (jsonArray != null) {
                    for (i in 0 until jsonArray.length()){

                        val data = jsonArray.optJSONObject(i)
                        val modelBaseKomik = ModelBaseKomik<String>()

                        modelBaseKomik.title = data.optString("title")
                        modelBaseKomik.thumbnail = data.optString("image")
                        modelBaseKomik.endPoint = data.optString("endpoint")
                        modelBaseKomik.type = data.optString("type")
                        listKomikTerbaruBackup.add(modelBaseKomik)
                    }
                    adapterTerbaru(listKomikTerbaruBackup)
                }
            }

            override fun onHttpsError(anError: ANError?) {
                dismissDialogLoading()
                Toast.makeText(context, "Error on : " + anError!!.errorDetail, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun adapterTerbaru(listKomikTerbaruBackup: MutableList<ModelBaseKomik<String>>) {

        val adapter = AdapterKomik(context, listKomikTerbaruBackup)
        binding.rvRelease.layoutManager = GridLayoutManager(context, 3)
        binding.rvRelease.adapter = adapter

        getBackupManhwa()
    }

    private fun getBackupManhwa(){

        Networking.HttpsRequest(Endpoints.KOMIK_TYPE_MANHWA_BACKUP, activity,object : Networking.Response{
            override fun onHttpsResponse(jsonObject: JSONObject) {

                val jsonArray = jsonObject.optJSONArray("data")

                if (jsonArray != null) {
                    for (i in 0 until 30){

                        val data = jsonArray.optJSONObject(i)
                        val modelBaseKomik = ModelBaseKomik<String>()

                        modelBaseKomik.title = data.optString("title")
                        modelBaseKomik.thumbnail = data.optString("image")
                        modelBaseKomik.endPoint = data.optString("endpoint")
                        modelBaseKomik.type = "Manhwa"
                        listKomikManhwaBackup.add(modelBaseKomik)
                    }
                    adapterManhwa(listKomikManhwaBackup)
                }
            }

            override fun onHttpsError(anError: ANError?) {
                dismissDialogLoading()
                Toast.makeText(context, "Error on : " + anError!!.errorDetail, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun adapterManhwa(listKomikManhwaBackup: MutableList<ModelBaseKomik<String>>) {

        val adapter = AdapterKomik(context, listKomikManhwaBackup)
        binding.rvKomikKorea.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvKomikKorea.adapter = adapter

        getBackupManhua()
    }

    private fun getBackupManhua(){

        Networking.HttpsRequest(Endpoints.KOMIK_TYPE_MANHUA_BACKUP, activity,object : Networking.Response{
            override fun onHttpsResponse(jsonObject: JSONObject) {

                val jsonArray = jsonObject.optJSONArray("data")

                if (jsonArray != null) {
                    for (i in 0 until 30){

                        val data = jsonArray.optJSONObject(i)
                        val modelBaseKomik = ModelBaseKomik<String>()

                        modelBaseKomik.title = data.optString("title")
                        modelBaseKomik.thumbnail = data.optString("image")
                        modelBaseKomik.endPoint = data.optString("endpoint")
                        modelBaseKomik.type = "Manhua"
                        listKomikManhuaBackup.add(modelBaseKomik)
                    }
                    adapterManhua(listKomikManhuaBackup)
                }
            }

            override fun onHttpsError(anError: ANError?) {
                dismissDialogLoading()
                Toast.makeText(context, "Error on : " + anError!!.errorDetail, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun adapterManhua(listKomikManhuaBckup: MutableList<ModelBaseKomik<String>>) {

        val adapter = AdapterKomik(context, listKomikManhuaBckup)
        binding.rvKomikChina.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvKomikChina.adapter = adapter

        dismissDialogLoading()
    }

}
package com.komikatow.komiku.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.androidnetworking.error.ANError
import com.komikatow.komiku.adapter.AdapterKomik
import com.komikatow.komiku.databinding.ActivitySearchBinding
import com.komikatow.komiku.model.ModelBaseKomik
import com.komikatow.komiku.utils.Endpoints
import com.komikatow.komiku.utils.Networking
import org.json.JSONObject

class SearchActivity : BaseActivity <ActivitySearchBinding> () {

    private val listSearch:MutableList<ModelBaseKomik <String> > = ArrayList()

    override fun createBinding(layoutInflater: LayoutInflater): ActivitySearchBinding {
        return ActivitySearchBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.toolbar.setNavigationOnClickListener { finish() }
        getTextInSearchEditText()

    }

    private fun getTextInSearchEditText() {

        binding.searchBar.setOnQueryTextListener(object : OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                binding.toolbar.title = newText
                binding.searchProgress.visibility = View.VISIBLE
                listSearch.clear()

                Networking.HttpsRequest(Endpoints.KOMIK_ALL_SEARCH+newText+"/?page=1",this@SearchActivity, object : Networking.Response{

                    @SuppressLint("NotifyDataSetChanged")
                    override fun onHttpsResponse(jsonObject: JSONObject) {

                        val jsonArray = jsonObject.getJSONArray("mangas")
                        for (i in 0 until jsonArray.length()){

                            val mangas = jsonArray.getJSONObject(i)
                            val link = mangas.getJSONObject("link")
                            val modelBaseKomik = ModelBaseKomik<String> ()

                            modelBaseKomik.title = mangas.getString("name")
                            modelBaseKomik.thumbnail = mangas.getString("thumb")
                            modelBaseKomik.type = " "
                            modelBaseKomik.endPoint = link.getString("endpoint")

                            val adapterKomik = AdapterKomik(this@SearchActivity, listSearch)
                            listSearch.add(modelBaseKomik)
                            adapterKomik.notifyDataSetChanged()

                        }
                        adapterSearch(listSearch)
                    }

                    override fun onHttpsError(anError: ANError) {
                       anError.printStackTrace()
                        Toast.makeText(this@SearchActivity, Endpoints.KOMIK_ALL_SEARCH+newText+"/?page=1", Toast.LENGTH_SHORT).show()
                    }
                })

                return true
            }
        })
    }

    private fun adapterSearch(listSearch: MutableList<ModelBaseKomik<String>>) {

        val adapterKomik = AdapterKomik(this, listSearch)
        binding.searchResult.layoutManager = GridLayoutManager(this, 3)
        binding.searchResult.adapter = adapterKomik
        binding.searchProgress.visibility = View.INVISIBLE

    }

}
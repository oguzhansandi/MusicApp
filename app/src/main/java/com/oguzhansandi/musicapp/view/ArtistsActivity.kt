package com.oguzhansandi.musicapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.oguzhansandi.musicapp.adapter.ArtistsRecyclerViewAdapter
import com.oguzhansandi.musicapp.databinding.ActivityArtistBinding
import com.oguzhansandi.musicapp.model.ArtistsModel
import com.oguzhansandi.musicapp.model.ArtistsResponseModel
import com.oguzhansandi.musicapp.service.ArtistsAPI
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.collections.ArrayList

class ArtistsActivity : AppCompatActivity(), ArtistsRecyclerViewAdapter.Listener {

    private lateinit var binding : ActivityArtistBinding
    private val BASE_URL = "https://api.deezer.com/"
    private var artistsModel : ArrayList<ArtistsModel>? = null
    private var artistList : ArtistsResponseModel? = null
    private var artistsRecyclerViewAdapter : ArtistsRecyclerViewAdapter? = null
    private var job : Job? = null
    private var categoryId = 0


    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error : ${throwable.localizedMessage}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //RecyclerView
        categoryId= intent.getIntExtra("categoryId",0)
        println("categoryId:: $categoryId")
        binding.recyclerViewArtist.layoutManager = GridLayoutManager(this,2)
        loadData()
    }

    private fun loadData(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ArtistsAPI::class.java)

        job = CoroutineScope(Dispatchers.IO).launch{
            val response = retrofit.getArtistsData(categoryId)

            withContext(Dispatchers.Main + exceptionHandler){
                if (response.isSuccessful){
                    response.body()?.let {
                        artistList = it
                        println("model : ${it.data?.size}")
                        //   genreModels = ArrayList(it)
                        artistList?.data?.let {
                            artistsRecyclerViewAdapter = ArtistsRecyclerViewAdapter(it,this@ArtistsActivity)
                            binding.recyclerViewArtist.adapter = artistsRecyclerViewAdapter
                            binding.recyclerViewArtist.setHasFixedSize(true)
                        }


                    }
                }
            }
        }

    }
    override fun onItemClick(artistsModel: ArtistsModel){
        val intent = Intent(this,AlbumActivity::class.java)
        intent.putExtra("artistId",artistsModel.id)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

}

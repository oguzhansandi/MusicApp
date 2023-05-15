package com.oguzhansandi.musicapp.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.oguzhansandi.musicapp.R
import com.oguzhansandi.musicapp.adapter.AlbumRecyclerViewAdapter
import com.oguzhansandi.musicapp.databinding.ActivityAlbumBinding
import com.oguzhansandi.musicapp.model.ArtistAlbumModel
import com.oguzhansandi.musicapp.model.ArtistAlbumResponseModel
import com.oguzhansandi.musicapp.model.ArtistModel
import com.oguzhansandi.musicapp.service.AlbumAPI
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AlbumActivity : AppCompatActivity(), AlbumRecyclerViewAdapter.Listener{

    private lateinit var binding : ActivityAlbumBinding
    private val BASE_URL = "https://api.deezer.com/"
    private var artistModel : ArtistModel? = null
    private var artistAlbumList : ArtistAlbumResponseModel? = null
    private var albumRecyclerViewAdapter : AlbumRecyclerViewAdapter? = null
    private var job : Job? = null
    private var artistId = 0


    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error : ${throwable.localizedMessage}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlbumBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //RecyclerView
        artistId= intent.getIntExtra("artistId",0)
        println("artistId:: $artistId")
        binding.recyclerViewAlbum.layoutManager = GridLayoutManager(this,1)
        loadData()
        loadAlbums()
    }

    private fun loadData(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AlbumAPI::class.java)

        job = CoroutineScope(Dispatchers.IO).launch{
            val response = retrofit.getAlbumData(artistId)

            withContext(Dispatchers.Main + exceptionHandler){
                if (response.isSuccessful){
                    response.body()?.let { albumResponse ->
                        artistModel = albumResponse
                        println("model ${albumResponse.nb_album}")

                        artistModel?.let {
                            binding.artistsText.text = it.name
                            Glide
                                .with(this@AlbumActivity)
                                .load(it.picture_medium)
                                .centerCrop()
                                .placeholder(R.mipmap.ic_launcher)
                                .into(binding.artistsImage);
                        }


                    }
                }
            }
        }

    }

    private fun loadAlbums(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AlbumAPI::class.java)

        job = CoroutineScope(Dispatchers.IO).launch{
            val response = retrofit.getAlbums(artistId)

            withContext(Dispatchers.Main + exceptionHandler){
                if (response.isSuccessful){
                    response.body()?.let { albumResponse ->
                        artistAlbumList = albumResponse
                        println("model ${albumResponse.data?.size}")

                        artistAlbumList!!.data?.let {
                            albumRecyclerViewAdapter = AlbumRecyclerViewAdapter(it,this@AlbumActivity)
                               binding.recyclerViewAlbum.adapter = albumRecyclerViewAdapter
                               binding.recyclerViewAlbum.setHasFixedSize(true)
                        }


                    }
                }
            }
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

    override fun onItemClick(artistAlbumModel: ArtistAlbumModel) {
        val intent = Intent(this,SongsActivity::class.java)
        intent.putExtra("albumId",artistAlbumModel.id)
        intent.putExtra("albumName",artistAlbumModel.title)
        startActivity(intent)

    }

}
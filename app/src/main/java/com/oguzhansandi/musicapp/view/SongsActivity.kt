package com.oguzhansandi.musicapp.view

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.oguzhansandi.musicapp.adapter.SongsRecyclerViewAdapter
import com.oguzhansandi.musicapp.databinding.ActivitySongsBinding
import com.oguzhansandi.musicapp.model.ArtistAlbumResponseModel
import com.oguzhansandi.musicapp.model.ArtistSongsModel
import com.oguzhansandi.musicapp.model.ArtistSongsResponseModel
import com.oguzhansandi.musicapp.service.SongsAPI
import kotlinx.android.synthetic.main.activity_main.recyclerView
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SongsActivity : AppCompatActivity(), SongsRecyclerViewAdapter.Listener {
    private lateinit var binding : ActivitySongsBinding
    private val BASE_URL = "https://api.deezer.com/"
    private var artistAlbum : ArtistAlbumResponseModel? = null
    private var artistsSongsList : ArtistSongsResponseModel? = null
    private var songsRecyclerViewAdapter : SongsRecyclerViewAdapter? = null
    private var job : Job? = null
    private var albumId = 0
    private var songsId = 0
    private var albumName = ""
    var mediaPlayer = MediaPlayer()


    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error : ${throwable.localizedMessage}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //RecyclerView
        albumId = intent.getIntExtra("albumId",0)
        albumName = intent.getStringExtra("albumName").toString()
        println("albumId:: $albumId")
        binding.albumText.text = albumName
        binding.recyclerViewSongs.layoutManager = GridLayoutManager(this,1)
        loadData()


    }

    private fun loadData(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SongsAPI::class.java)

        job = CoroutineScope(Dispatchers.IO).launch{
            val response = retrofit.getSongs(albumId)

            withContext(Dispatchers.Main + exceptionHandler){
                if (response.isSuccessful){
                    response.body()?.let { songResponse->
                        artistsSongsList = songResponse
                        println("model ${songResponse?.data?.size}")

                        artistsSongsList!!.data?.let {
                            songsRecyclerViewAdapter = SongsRecyclerViewAdapter(it,this@SongsActivity)
                            binding.recyclerViewSongs.adapter = songsRecyclerViewAdapter
                            binding.recyclerViewSongs.setHasFixedSize(true)
                        }


                    }
                }
            }
        }


    }

    override fun onItemClick(artistSongsModel: ArtistSongsModel) {
        var url = Uri.parse(artistSongsModel.preview)

       initMediaPlayer(url)
    }

    private fun initMediaPlayer(url: Uri){
        if (mediaPlayer.isPlaying){
            mediaPlayer.stop()
            mediaPlayer.reset()
            mediaPlayer.setDataSource(this,url)
            mediaPlayer.prepare()
            mediaPlayer.start()
        }else{
            mediaPlayer.setDataSource(this,url)
            mediaPlayer.prepare()
            mediaPlayer.start()
        }
    }
    /*
        private fun loadSongs(){
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SongsAPI::class.java)

            job = CoroutineScope(Dispatchers.IO).launch{
                val response = retrofit.getAlbum(albumId)

                withContext(Dispatchers.Main + exceptionHandler){
                    if (response.isSuccessful){
                        response.body()?.let {
                            artistAlbum = albumResponse
                            println("model ${albumResponse?.data?.size}")

                            artistAlbum!!.data?.let {
                                binding.albumText.text =
                            }


                        }
                    }
                }
            }

    }*/


    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }


}
package com.oguzhansandi.musicapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oguzhansandi.musicapp.R
import com.oguzhansandi.musicapp.adapter.RecyclerViewAdapter
import com.oguzhansandi.musicapp.databinding.ActivityMainBinding
import com.oguzhansandi.musicapp.model.GenreModel
import com.oguzhansandi.musicapp.model.GenreResponseModel
import com.oguzhansandi.musicapp.service.GenreAPI
import kotlinx.android.synthetic.main.activity_main.recyclerView
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.Listener {

    lateinit var binding: ActivityMainBinding
    private val BASE_URL = "https://api.deezer.com/"
    private var genreModels : ArrayList<GenreModel>? = null
    private var genreList : GenreResponseModel? = null
    private var recyclerViewAdapter : RecyclerViewAdapter? = null
    private var job : Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error : ${throwable.localizedMessage}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //RecyclerView

        val layoutManager : RecyclerView.LayoutManager = GridLayoutManager(this@MainActivity,2)
        recyclerView.layoutManager = layoutManager
        loadData()
    }



    private fun loadData(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GenreAPI::class.java)

        job = CoroutineScope(Dispatchers.IO).launch{
            val response = retrofit.getData()

            withContext(Dispatchers.Main + exceptionHandler){
                if (response.isSuccessful){
                    response.body()?.let {
                        genreList = it
                        println("model ${genreList?.data?.size}")
                        genreList?.data?.let {
                            recyclerViewAdapter = RecyclerViewAdapter(it,this@MainActivity)
                            binding.recyclerView.setHasFixedSize(true)
                        }
                        recyclerView.adapter = recyclerViewAdapter


                    }
                }
            }
        }

    }
    override fun onItemClick(genreModel: GenreModel){
        val intent = Intent(this,ArtistsActivity::class.java)
        intent.putExtra("categoryId",genreModel.id)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

}

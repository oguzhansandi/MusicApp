package com.oguzhansandi.musicapp.adapter

import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oguzhansandi.musicapp.R
import com.oguzhansandi.musicapp.model.ArtistSongsModel
import kotlinx.android.synthetic.main.album_layout.view.album_image
import kotlinx.android.synthetic.main.songs_layout.view.song_name

class SongsRecyclerViewAdapter(private val artistSongsList : ArrayList<ArtistSongsModel>, private val listener : Listener) : RecyclerView.Adapter<SongsRecyclerViewAdapter.SongsRowHolder>() {
    interface Listener{
        fun onItemClick(artistSongsModel: ArtistSongsModel)
    }



    class SongsRowHolder(view: View) : RecyclerView.ViewHolder(view) {



        fun bind(artistSongsModel: ArtistSongsModel, position: Int, listener : Listener) {

            itemView.setOnClickListener {
                listener.onItemClick(artistSongsModel)
            }



/*
            val url = artistSongsModel.md5_image

            Glide
                .with(itemView.context)
                .load(url)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(itemView.album_image);
*/

            itemView.song_name.text = artistSongsModel.title
           // itemView.album_duration.text = artistSongsModel.duration

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsRowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.songs_layout,parent,false)
        return SongsRowHolder(view)
    }

    override fun getItemCount(): Int {
        return artistSongsList.count()
    }

    override fun onBindViewHolder(holder: SongsRowHolder, position: Int) {
        holder.bind(artistSongsList[position],position,listener)
    }
}
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
import kotlinx.android.synthetic.main.artist_layout.view.artists_image
import kotlinx.android.synthetic.main.row_layout.view.image_view
import kotlinx.android.synthetic.main.songs_layout.view.song_image
import kotlinx.android.synthetic.main.songs_layout.view.song_name

class SongsRecyclerViewAdapter(private val artistSongsList : ArrayList<ArtistSongsModel>, private val listener : Listener, private val photo: String) : RecyclerView.Adapter<SongsRecyclerViewAdapter.SongsRowHolder>() {

    interface Listener{
        fun onItemClick(artistSongsModel: ArtistSongsModel)
    }
    class SongsRowHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(artistSongsModel: ArtistSongsModel, position: Int, listener : Listener) {
            itemView.setOnClickListener {
                listener.onItemClick(artistSongsModel)
            }

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

        Glide
            .with(holder.itemView.context)
            .load(photo)
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher)
            .into(holder.itemView.song_image);
    }
}
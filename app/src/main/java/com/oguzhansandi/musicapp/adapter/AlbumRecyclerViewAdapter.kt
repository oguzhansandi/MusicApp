package com.oguzhansandi.musicapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oguzhansandi.musicapp.R
import com.oguzhansandi.musicapp.model.ArtistAlbumModel
import kotlinx.android.synthetic.main.album_layout.view.album_date
import kotlinx.android.synthetic.main.album_layout.view.album_image
import kotlinx.android.synthetic.main.album_layout.view.album_name

class AlbumRecyclerViewAdapter(private val albumList : ArrayList<ArtistAlbumModel>, private val listener : Listener) : RecyclerView.Adapter<AlbumRecyclerViewAdapter.AlbumRowHolder>()  {

    interface Listener{
        fun onItemClick(artistAlbumModel: ArtistAlbumModel)
    }

    class AlbumRowHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(artistAlbumModel: ArtistAlbumModel, position: Int, listener : Listener) {
            itemView.setOnClickListener {
                listener.onItemClick(artistAlbumModel)
            }

            val url = artistAlbumModel.cover_medium

            Glide
                .with(itemView.context)
                .load(url)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(itemView.album_image);
            itemView.album_name.text = artistAlbumModel.title
            itemView.album_date.text = artistAlbumModel.release_date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumRowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.album_layout,parent,false)
        return AlbumRowHolder(view)
    }

    override fun getItemCount(): Int {
        return albumList.count()
    }

    override fun onBindViewHolder(holder: AlbumRowHolder, position: Int) {
        holder.bind(albumList[position],position,listener)
    }

}
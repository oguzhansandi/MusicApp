package com.oguzhansandi.musicapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oguzhansandi.musicapp.R
import com.oguzhansandi.musicapp.model.ArtistsModel
import kotlinx.android.synthetic.main.artist_layout.view.artists_image
import kotlinx.android.synthetic.main.artist_layout.view.artists_text

class ArtistsRecyclerViewAdapter(private val artistsList : ArrayList<ArtistsModel>,private val listener : Listener) : RecyclerView.Adapter<ArtistsRecyclerViewAdapter.ArtistsRowHolder>() {

    interface Listener{
        fun onItemClick(artistsModel: ArtistsModel)
    }

    class ArtistsRowHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(artistsModel: ArtistsModel, position: Int, listener : Listener) {
            itemView.setOnClickListener {
                listener.onItemClick(artistsModel)
            }

            val url = artistsModel.picture_medium

            Glide
                .with(itemView.context)
                .load(url)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(itemView.artists_image);

            itemView.artists_text.text = artistsModel.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistsRowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.artist_layout,parent,false)
        return ArtistsRowHolder(view)
    }

    override fun getItemCount(): Int {
        return artistsList.count()
    }

    override fun onBindViewHolder(holder: ArtistsRowHolder, position: Int) {
        holder.bind(artistsList[position],position,listener)
    }

}
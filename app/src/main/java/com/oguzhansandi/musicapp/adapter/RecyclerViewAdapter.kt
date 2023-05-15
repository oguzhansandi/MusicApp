package com.oguzhansandi.musicapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oguzhansandi.musicapp.R
import com.oguzhansandi.musicapp.model.GenreModel
import kotlinx.android.synthetic.main.row_layout.view.image_view
import kotlinx.android.synthetic.main.row_layout.view.text_view

class RecyclerViewAdapter(private val genreList : ArrayList<GenreModel>,private val listener : Listener) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {

    interface Listener{
        fun onItemClick(genreModel: GenreModel)
    }

    class RowHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(genreModel: GenreModel, position: Int, listener : Listener) {
            itemView.setOnClickListener {
                listener.onItemClick(genreModel)
            }

            val url = genreModel.picture_medium

            Glide
                .with(itemView.context)
                .load(url)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(itemView.image_view);

            itemView.text_view.text = genreModel.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout,parent,false)
        return RowHolder(view)
    }

    override fun getItemCount(): Int {
        return genreList.count()
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(genreList[position],position,listener)
    }

}
package com.karrar.marvelheroes.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.karrar.marvelheroes.R
import com.karrar.marvelheroes.data.marvelResponse.MarvelResponse
import com.karrar.marvelheroes.data.marvelResponse.MovieResponse
import com.karrar.marvelheroes.databinding.ItemMovieBinding

class MovieAdapter(private val list: List<MovieResponse?>): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = list[position]
        holder.binding.apply {
            textTitle.text = currentItem?.title
            Glide.with(imageCover.context).load(currentItem?.coverUrl).into(imageCover)
            textReleaseDate.text = currentItem?.releaseDate
        }
    }

    override fun getItemCount() = list.size

    class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding = ItemMovieBinding.bind(itemView)
    }
}

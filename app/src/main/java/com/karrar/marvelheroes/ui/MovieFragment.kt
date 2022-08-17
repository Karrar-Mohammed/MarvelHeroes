package com.karrar.marvelheroes.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.karrar.marvelheroes.data.Repository
import com.karrar.marvelheroes.data.State
import com.karrar.marvelheroes.data.marvelResponse.MovieResponse
import com.karrar.marvelheroes.databinding.FragmentMovieBinding
import kotlinx.coroutines.launch


class MovieFragment : BaseFragment<FragmentMovieBinding>() {


    override fun setup() {
        lifecycleScope.launch {
            Repository.getMovie(10).collect{ response ->
                when(response){
                    is State.Error -> {}
                    State.Loading -> {}
                    is State.Success -> {
                        bindDataToUi(response.data)
                    }
                }
            }
        }

    }

    private fun bindDataToUi(response: MovieResponse?) {
        binding.apply {
            Glide.with(imageMovieCover).load(response?.coverUrl).into(imageMovieCover)
            textMovieTitle.text = response?.title
            textMovieReleaseDate.text = response?.releaseDate
            textMovieDescription.text = response?.overview
            textRelatedMovies.visibility = View.VISIBLE

            response?.let {
                recyclerRelatedMovies.adapter = MovieAdapter(response.relatedMovies)

            }
        }
    }

    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentMovieBinding
        get() = FragmentMovieBinding::inflate

}
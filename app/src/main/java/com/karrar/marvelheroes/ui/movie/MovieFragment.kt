package com.karrar.marvelheroes.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.karrar.marvelheroes.data.repository.Repository
import com.karrar.marvelheroes.data.State
import com.karrar.marvelheroes.data.marvelResponse.MovieResponse
import com.karrar.marvelheroes.databinding.FragmentMovieBinding
import com.karrar.marvelheroes.ui.adapter.MovieAdapter
import com.karrar.marvelheroes.ui.movieInteraction.MovieItemInteraction
import com.karrar.marvelheroes.ui.base.BaseFragment
import com.karrar.marvelheroes.util.Constants
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch


class MovieFragment : BaseFragment<FragmentMovieBinding>(), MovieItemInteraction {

    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentMovieBinding
        get() = FragmentMovieBinding::inflate

    override fun setup() {
        val movieId = arguments?.getInt(Constants.MOVIE_ID_KEY)
        getMovie(movieId)

    }

    private fun getMovie(movieId: Int?) {
        lifecycleScope.launch {
            Repository.getMovie(movieId).catch {
                onError()
            }.collect{ response ->
                when(response){
                    is State.Error -> {
                        Toast.makeText(this@MovieFragment.context, response.message, Toast.LENGTH_SHORT).show()
                    }
                    State.Loading -> {
                        onLoading()
                    }
                    is State.Success -> {
                        onSuccess(response.data)
                    }
                }
            }
        }
    }

    private fun onError() {
        binding.progressMovie.visibility = View.GONE
        Toast.makeText(this@MovieFragment.context, "an error has occurred", Toast.LENGTH_SHORT).show()
    }

    private fun onLoading() {
        binding.apply {
            progressMovie.visibility = View.VISIBLE
            imageMovieCover.visibility = View.INVISIBLE
            textMovieTitle.visibility = View.INVISIBLE
            textMovieReleaseDate.visibility = View.INVISIBLE
            textMovieDescription.visibility = View.INVISIBLE
            textRelatedMovies.visibility = View.INVISIBLE
            recyclerRelatedMovies.visibility = View.INVISIBLE
        }
    }

    private fun onSuccess(response: MovieResponse?) {
        binding.apply {
            progressMovie.visibility = View.GONE

            Glide.with(imageMovieCover).load(response?.coverUrl).into(imageMovieCover)
            textMovieTitle.text = response?.title
            textMovieReleaseDate.text = response?.releaseDate
            textMovieDescription.text = response?.overview

            textRelatedMovies.visibility = View.VISIBLE
            imageMovieCover.visibility = View.VISIBLE
            textMovieTitle.visibility = View.VISIBLE
            textMovieReleaseDate.visibility = View.VISIBLE
            textMovieDescription.visibility = View.VISIBLE
            textRelatedMovies.visibility = View.VISIBLE
            recyclerRelatedMovies.visibility = View.VISIBLE

            response?.let {
                recyclerRelatedMovies.adapter = MovieAdapter(response.relatedMovies, this@MovieFragment)

            }
        }
    }

    companion object {
        fun newInstance(movieId: Int): MovieFragment {
            return MovieFragment().apply {
                arguments = Bundle().apply {
                    putInt(Constants.MOVIE_ID_KEY, movieId)
                }
            }
        }
    }

    override fun onMovieClicked(movieId: Int?) {
        getMovie(movieId)
    }

}
package com.karrar.marvelheroes.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.PagerSnapHelper
import com.karrar.marvelheroes.data.Repository
import com.karrar.marvelheroes.data.State
import com.karrar.marvelheroes.data.marvelResponse.MovieResponse
import com.karrar.marvelheroes.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun setup() {
        lifecycleScope.launch {
            Repository.getMoviesList().collect{ response ->
                when(response){
                    is State.Error -> {}
                    State.Loading -> {}
                    is State.Success -> response.data?.moviesList?.let {
                        setupRecycler(it)
                    }
                }
            }
        }
    }

    private fun setupRecycler(moviesList: List<MovieResponse>) {
        binding.apply {
            PagerSnapHelper().attachToRecyclerView(homeRecycler)
            val adapter = MovieAdapter(moviesList)
            homeRecycler.adapter = adapter
        }
    }

    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

}
package com.karrar.marvelheroes.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.PagerSnapHelper
import com.karrar.marvelheroes.R
import com.karrar.marvelheroes.data.Repository
import com.karrar.marvelheroes.data.State
import com.karrar.marvelheroes.data.marvelResponse.MovieResponse
import com.karrar.marvelheroes.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment<FragmentHomeBinding>(), MovieItemInteraction {

    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

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
            val adapter = MovieAdapter(moviesList, this@HomeFragment)
            homeRecycler.adapter = adapter
        }
    }

    override fun onMovieClicked(movieId: Int?) {
        movieId?.let {
            val movieFragment = MovieFragment.newInstance(movieId)
            replaceFragment(movieFragment)
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
        }.commit()

    }

}
package com.karrar.marvelheroes.ui.home


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.PagerSnapHelper
import com.karrar.marvelheroes.R
import com.karrar.marvelheroes.data.repository.Repository
import com.karrar.marvelheroes.data.State
import com.karrar.marvelheroes.data.marvelResponse.MovieResponse
import com.karrar.marvelheroes.databinding.FragmentHomeBinding
import com.karrar.marvelheroes.ui.base.BaseFragment
import com.karrar.marvelheroes.ui.adapter.MovieAdapter
import com.karrar.marvelheroes.ui.movie.MovieFragment
import com.karrar.marvelheroes.ui.movieInteraction.MovieItemInteraction
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment<FragmentHomeBinding>(), MovieItemInteraction {

    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun setup() {
        getMoviesList()
        callBacks()
    }

    private fun callBacks() {
        binding.buttonTryAgain.setOnClickListener {
            getMoviesList()
        }
    }

    private fun getMoviesList() {
        lifecycleScope.launch {
            Repository.getMoviesList()
                .catch {
                    onError()
                }.collect{ response ->
                when(response){
                    is State.Error -> {
                        Toast.makeText(this@HomeFragment.context, response.message, Toast.LENGTH_SHORT).show()
                    }
                    State.Loading -> {
                        onLoading()
                    }
                    is State.Success -> response.data?.moviesList?.let {
                        onSuccess(it)
                    }
                }
            }
        }
    }

    private fun onError() {
        binding.apply {
            progressHome.visibility = View.GONE
            buttonTryAgain.visibility = View.VISIBLE
        }
        Toast.makeText(this@HomeFragment.context, "an error has occurred", Toast.LENGTH_SHORT).show()
    }

    private fun onLoading() {
        binding.apply {
            buttonTryAgain.visibility = View.GONE
            progressHome.visibility = View.VISIBLE
            recyclerHome.visibility = View.INVISIBLE
        }
    }


    private fun onSuccess(moviesList: List<MovieResponse>) {
        binding.apply {
            progressHome.visibility = View.GONE
            recyclerHome.visibility = View.VISIBLE
            buttonTryAgain.visibility = View.GONE

            PagerSnapHelper().attachToRecyclerView(recyclerHome)
            val adapter = MovieAdapter(moviesList, this@HomeFragment)
            recyclerHome.adapter = adapter
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
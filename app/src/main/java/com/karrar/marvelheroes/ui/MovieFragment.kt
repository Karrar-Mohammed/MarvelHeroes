package com.karrar.marvelheroes.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.karrar.marvelheroes.databinding.FragmentMovieBinding


class MovieFragment : BaseFragment<FragmentMovieBinding>() {
    override fun setup() {

    }

    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentMovieBinding
        get() = FragmentMovieBinding::inflate

}
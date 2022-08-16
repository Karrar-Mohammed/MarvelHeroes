package com.karrar.marvelheroes.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import com.karrar.marvelheroes.databinding.FragmentHomeBinding


class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun setup() {

    }

    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

}
package com.karrar.marvelheroes.data.marvelResponse


import com.google.gson.annotations.SerializedName

data class MarvelResponse(
    @SerializedName("data")
    val moviesList: List<MovieResponse>?,
    @SerializedName("total")
    val total: Int?
)
package com.karrar.marvelheroes.data.marvelResponse


import com.google.gson.annotations.SerializedName
import com.karrar.marvelheroes.data.marvelResponse.MovieResponse

data class MarvelResponse(
    @SerializedName("data")
    val data: List<MovieResponse?>?,
    @SerializedName("total")
    val total: Int?
)
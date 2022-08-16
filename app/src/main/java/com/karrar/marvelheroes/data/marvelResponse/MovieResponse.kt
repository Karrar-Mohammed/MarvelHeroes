package com.karrar.marvelheroes.data.marvelResponse


import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("cover_url")
    val coverUrl: String?,
    @SerializedName("trailer_url")
    val trailerUrl: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("box_office")
    val boxOffice: String?,
    @SerializedName("chronology")
    val chronology: Int?,
    @SerializedName("directed_by")
    val directedBy: String?,
    @SerializedName("duration")
    val duration: Int?,
    @SerializedName("imdb_id")
    val imdbId: String?,
    @SerializedName("phase")
    val phase: Int?,
    @SerializedName("post_credit_scenes")
    val postCreditScenes: Int?,
    @SerializedName("saga")
    val saga: String?,
    @SerializedName("related_movies")
    val relatedMovies: List<MovieResponse?>?
)
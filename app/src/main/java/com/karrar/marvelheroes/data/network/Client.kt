package com.karrar.marvelheroes.data.network

import com.google.gson.Gson
import com.karrar.marvelheroes.data.State
import com.karrar.marvelheroes.data.marvelResponse.MarvelResponse
import com.karrar.marvelheroes.data.marvelResponse.MovieResponse
import com.karrar.marvelheroes.util.Constants
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

object Client {
    private val client = OkHttpClient()

    fun getMarvelMoviesList(): State<MarvelResponse> {
        val response = makeRequest(getMarvelMoviesUrl())
        return if (response.isSuccessful) {

            val result = Gson().fromJson(response.body?.string(), MarvelResponse::class.java)
            State.Success(result)

        } else {
            State.Error(response.message)
        }


    }

    fun getMovie(movieId: Int): State<MovieResponse> {
        val response = makeRequest(getMovieUrl(movieId))
        return if (response.isSuccessful) {

            val result = Gson().fromJson(response.body?.string(), MovieResponse::class.java)
            State.Success(result)

        } else {
            State.Error(response.message)
        }
    }

    private fun makeRequest(url: HttpUrl): Response {
        val request = Request.Builder().url(url).build()
        return client.newCall(request).execute()
    }

    private fun getMovieUrl(movieId: Int): HttpUrl {
        return HttpUrl.Builder()
            .scheme(Constants.Api.SCHEME)
            .host(Constants.Api.HOST)
            .addPathSegments(Constants.Api.PATH_SEGMENT)
            .addPathSegment(movieId.toString())
            .build()
    }

    private fun getMarvelMoviesUrl(): HttpUrl {
        return HttpUrl.Builder()
            .scheme(Constants.Api.SCHEME)
            .host(Constants.Api.HOST)
            .addPathSegments(Constants.Api.PATH_SEGMENT)
            .build()
    }
}

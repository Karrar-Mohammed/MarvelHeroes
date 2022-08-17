package com.karrar.marvelheroes.data.repository

import com.karrar.marvelheroes.data.State
import com.karrar.marvelheroes.data.marvelResponse.MarvelResponse
import com.karrar.marvelheroes.data.marvelResponse.MovieResponse
import com.karrar.marvelheroes.data.network.Client
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object Repository {

    fun getMoviesList(): Flow<State<MarvelResponse>> {
        return flow {
            emit(State.Loading)
            emit(Client.getMarvelMoviesList())
        }.flowOn(Dispatchers.IO)
    }

    fun getMovie(movieId: Int?): Flow<State<MovieResponse>> {
        return flow {
            emit(State.Loading)
            emit(Client.getMovie(movieId))
        }.flowOn(Dispatchers.IO)
    }
}
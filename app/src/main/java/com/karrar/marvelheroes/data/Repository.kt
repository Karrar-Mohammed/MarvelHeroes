package com.karrar.marvelheroes.data

import com.karrar.marvelheroes.data.marvelResponse.MarvelResponse
import com.karrar.marvelheroes.data.marvelResponse.MovieResponse
import com.karrar.marvelheroes.data.network.Client
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object Repository {

    suspend fun getMoviesList(): Flow<State<MarvelResponse>> {
        return flow {
            emit(State.Loading)
            emit(Client.getMarvelMoviesList())
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovie(movieId: Int): Flow<State<MovieResponse>> {
        return flow {
            emit(State.Loading)
            emit(Client.getMovie(movieId))
        }.flowOn(Dispatchers.IO)
    }
}
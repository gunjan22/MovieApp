package com.example.myapplication.data.respository

import com.example.myapplication.ConstValue.API_KEY
import com.example.myapplication.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieDetailRepository @Inject constructor(private val api: ApiService){

fun getMovieDetail(movieId:Long)= flow{

        emit(api.getMovie(movieId,API_KEY))

    }.flowOn(Dispatchers.IO)


}
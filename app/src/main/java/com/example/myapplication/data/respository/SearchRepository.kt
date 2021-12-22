package com.example.myapplication.data.respository

import com.example.myapplication.data.remote.ApiService
import javax.inject.Inject

class SearchRepository @Inject constructor(private val api: ApiService) {

    fun getMovieSearch(searchQuery: String) = SearchMovieDatSource(api, searchQuery)


}
package com.example.myapplication.data.respository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication.ConstValue.API_KEY
import com.example.myapplication.data.model.MovieModel
import com.example.myapplication.data.remote.ApiService

class SearchMovieDatSource constructor(
    private val api: ApiService,
    private val searchQuery: String
) :
    PagingSource<Int, MovieModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = api.getMovieSearch(searchQuery, API_KEY, nextPageNumber)
            val responseData = mutableListOf<MovieModel>()
            val data = response.movieModels ?: emptyList()
            responseData.addAll(data)

            val prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1

            LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = nextPageNumber.plus(1)
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
        TODO("Not yet implemented")
    }
}

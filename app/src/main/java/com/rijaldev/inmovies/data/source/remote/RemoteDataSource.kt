package com.rijaldev.inmovies.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rijaldev.inmovies.BuildConfig
import com.rijaldev.inmovies.data.source.remote.response.ApiResponse
import com.rijaldev.inmovies.data.source.remote.response.api.ApiService
import com.rijaldev.inmovies.data.source.remote.response.movie.DetailMovieResponse
import com.rijaldev.inmovies.data.source.remote.response.movie.MoviesResponse
import com.rijaldev.inmovies.data.source.remote.response.movie.ResultsItemMovie
import com.rijaldev.inmovies.data.source.remote.response.tvshow.DetailTvShowResponse
import com.rijaldev.inmovies.data.source.remote.response.tvshow.ResultsItemTvShow
import com.rijaldev.inmovies.data.source.remote.response.tvshow.TvShowsResponse
import com.rijaldev.inmovies.utils.DataDummy
import com.rijaldev.inmovies.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor (private val apiService: ApiService) {

    fun getMovies(): LiveData<ApiResponse<List<ResultsItemMovie>>> {
        EspressoIdlingResource.increment()
        val resultMovies = MutableLiveData<ApiResponse<List<ResultsItemMovie>>>()
        val client  = apiService.getListMovies(BuildConfig.API_KEY, BuildConfig.PAGE)
        client.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                if (response.isSuccessful) {
                    resultMovies.value = ApiResponse.success(response.body()?.results as List<ResultsItemMovie>)
                } else {
                    resultMovies.value = ApiResponse.empty(response.message(), response.body()?.results as List<ResultsItemMovie>)
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                EspressoIdlingResource.decrement()
                resultMovies.value = ApiResponse.error(t.message.toString(), mutableListOf())
            }
        })
        return resultMovies
    }

    fun getDetailMovie(movieId: String): LiveData<ApiResponse<DetailMovieResponse>> {
        EspressoIdlingResource.increment()
        val resultDetail = MutableLiveData<ApiResponse<DetailMovieResponse>>()
        val client = apiService.getDetailMovie(movieId, BuildConfig.API_KEY)
        client.enqueue(object : Callback<DetailMovieResponse> {
            override fun onResponse(
                call: Call<DetailMovieResponse>,
                response: Response<DetailMovieResponse>
            ) {
                if (response.isSuccessful) {
                    resultDetail.value = ApiResponse.success(response.body() as DetailMovieResponse)
                } else {
                    resultDetail.value = ApiResponse.empty(response.message(), response.body() as DetailMovieResponse)
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
                EspressoIdlingResource.decrement()
                resultDetail.value = ApiResponse.error(t.message.toString(), DataDummy.getDetailMovieResponse())
            }
        })
        return resultDetail
    }

    fun getTvShows(): LiveData<ApiResponse<List<ResultsItemTvShow>>> {
        EspressoIdlingResource.increment()
        val resultTvShows = MutableLiveData<ApiResponse<List<ResultsItemTvShow>>>()
        val client = apiService.getTvShows(BuildConfig.API_KEY, BuildConfig.PAGE)
        client.enqueue(object : Callback<TvShowsResponse> {
            override fun onResponse(
                call: Call<TvShowsResponse>,
                response: Response<TvShowsResponse>
            ) {
                if (response.isSuccessful) {
                    resultTvShows.value = ApiResponse.success(response.body()?.results as List<ResultsItemTvShow>)
                } else {
                    resultTvShows.value = ApiResponse.empty(response.message(), response.body()?.results as List<ResultsItemTvShow>)
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvShowsResponse>, t: Throwable) {
                EspressoIdlingResource.decrement()
                resultTvShows.value = ApiResponse.error(t.message.toString(), mutableListOf())
            }
        })
        return resultTvShows
    }

    fun getDetailTvShow(tvId: String): LiveData<ApiResponse<DetailTvShowResponse>> {
        EspressoIdlingResource.increment()
        val resultDetailTvShow = MutableLiveData<ApiResponse<DetailTvShowResponse>>()
        val client = apiService.getDetailTvShow(tvId, BuildConfig.API_KEY)
        client.enqueue(object : Callback<DetailTvShowResponse> {
            override fun onResponse(
                call: Call<DetailTvShowResponse>,
                response: Response<DetailTvShowResponse>
            ) {
                if (response.isSuccessful) {
                    resultDetailTvShow.value = ApiResponse.success(response.body() as DetailTvShowResponse)
                } else {
                    resultDetailTvShow.value = ApiResponse.empty(response.message(), response.body() as DetailTvShowResponse)
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<DetailTvShowResponse>, t: Throwable) {
                EspressoIdlingResource.decrement()
                resultDetailTvShow.value = ApiResponse.error(t.message.toString(), DataDummy.getDetailTvShowResponse())
            }
        })
        return resultDetailTvShow
    }
}
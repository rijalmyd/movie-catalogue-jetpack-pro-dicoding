package com.rijaldev.inmovies.data.source.remote.response.api

import com.rijaldev.inmovies.data.source.remote.response.movie.DetailMovieResponse
import com.rijaldev.inmovies.data.source.remote.response.movie.MoviesResponse
import com.rijaldev.inmovies.data.source.remote.response.tvshow.DetailTvShowResponse
import com.rijaldev.inmovies.data.source.remote.response.tvshow.TvShowsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("3/movie/popular")
    fun getListMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: String
    ): Call<MoviesResponse>

    @GET("3/movie/{id}")
    fun getDetailMovie(
        @Path("id") id: String,
        @Query("api_key") apiKey: String
    ): Call<DetailMovieResponse>

    @GET("3/tv/popular")
    fun getTvShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: String
    ): Call<TvShowsResponse>

    @GET("3/tv/{id}")
    fun getDetailTvShow(
        @Path("id") id: String,
        @Query("api_key") apiKey: String
    ): Call<DetailTvShowResponse>
}
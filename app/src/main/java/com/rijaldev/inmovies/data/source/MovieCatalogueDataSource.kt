package com.rijaldev.inmovies.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.rijaldev.inmovies.data.source.local.entity.DetailEntity
import com.rijaldev.inmovies.data.source.local.entity.MovieEntity
import com.rijaldev.inmovies.data.source.local.entity.TvShowEntity
import com.rijaldev.inmovies.vo.Resource

interface  MovieCatalogueDataSource {

    fun getMovies(sort: String): LiveData<Resource<PagedList<MovieEntity>>>

    fun getDetailMovie(movieId: String): LiveData<Resource<DetailEntity>>

    fun getTvShows(sort: String): LiveData<Resource<PagedList<TvShowEntity>>>

    fun getDetailTvShow(tvId: String): LiveData<Resource<DetailEntity>>

    fun getFavoriteMovies(): LiveData<PagedList<DetailEntity>>

    fun getFavoriteTvShows(): LiveData<PagedList<DetailEntity>>

    fun setFavorite(detailEntity: DetailEntity, state: Boolean)
}
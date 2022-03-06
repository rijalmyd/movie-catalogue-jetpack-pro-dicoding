package com.rijaldev.inmovies.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.rijaldev.inmovies.data.source.local.entity.DetailEntity
import com.rijaldev.inmovies.data.source.local.entity.MovieEntity
import com.rijaldev.inmovies.data.source.local.entity.TvShowEntity
import com.rijaldev.inmovies.data.source.local.room.MovieCatalogueDao
import com.rijaldev.inmovies.utils.SortUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val movieCatalogueDao: MovieCatalogueDao) {

    fun getMovies(sort: String): DataSource.Factory<Int, MovieEntity> {
        val query = SortUtils.getSortedQueryMovies(sort)
        return movieCatalogueDao.getMovies(query)
    }

    fun getTvShows(sort: String): DataSource.Factory<Int, TvShowEntity> {
        val query = SortUtils.getSortedQueryTvShows(sort)
        return movieCatalogueDao.getTvShows(query)
    }

    fun getFavoriteMovies(): DataSource.Factory<Int, DetailEntity> =
        movieCatalogueDao.getFavoriteMovies()

    fun getFavoriteTvShows(): DataSource.Factory<Int, DetailEntity> =
        movieCatalogueDao.getFavoriteTvShows()

    fun getDetail(id: String): LiveData<DetailEntity> =
        movieCatalogueDao.getDetail(id)

    fun insertMovies(movieEntity: List<MovieEntity>) =
        movieCatalogueDao.insertMovie(movieEntity)

    fun insertTvShow(tvShowEntity: List<TvShowEntity>) =
        movieCatalogueDao.insertTvShow(tvShowEntity)

    fun insertDetail(detailEntity: DetailEntity) =
        movieCatalogueDao.insert(detailEntity)

    fun setFavorite(detailEntity: DetailEntity, newState: Boolean = true) {
        detailEntity.isFavorite = newState
        movieCatalogueDao.update(detailEntity)
    }
}

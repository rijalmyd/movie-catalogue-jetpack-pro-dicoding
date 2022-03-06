package com.rijaldev.inmovies.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.rijaldev.inmovies.data.source.local.entity.DetailEntity
import com.rijaldev.inmovies.data.source.local.entity.MovieEntity
import com.rijaldev.inmovies.data.source.local.entity.TvShowEntity

@Dao
interface MovieCatalogueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(detailEntity: DetailEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movieEntity: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShow(tvShowEntity: List<TvShowEntity>)

    @Update
    fun update(detailEntity: DetailEntity)

    @RawQuery(observedEntities = [MovieEntity::class])
    fun getMovies(query: SupportSQLiteQuery): DataSource.Factory<Int, MovieEntity>

    @RawQuery(observedEntities = [TvShowEntity::class])
    fun getTvShows(query: SupportSQLiteQuery): DataSource.Factory<Int, TvShowEntity>

    @Query("SELECT * FROM detail_entities WHERE is_favorite = 1 AND detail_type = 'movie'")
    fun getFavoriteMovies(): DataSource.Factory<Int, DetailEntity>

    @Query("SELECT * FROM detail_entities WHERE is_favorite = 1 AND detail_type = 'tv_show'")
    fun getFavoriteTvShows(): DataSource.Factory<Int, DetailEntity>

    @Query("SELECT * FROM detail_entities WHERE id = :id")
    fun getDetail(id: String): LiveData<DetailEntity>
}
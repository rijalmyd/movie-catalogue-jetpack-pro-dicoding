package com.rijaldev.inmovies.data.source

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.rijaldev.inmovies.data.source.local.LocalDataSource
import com.rijaldev.inmovies.data.source.local.entity.DetailEntity
import com.rijaldev.inmovies.data.source.local.entity.MovieEntity
import com.rijaldev.inmovies.data.source.local.entity.TvShowEntity
import com.rijaldev.inmovies.data.source.remote.RemoteDataSource
import com.rijaldev.inmovies.data.source.remote.response.ApiResponse
import com.rijaldev.inmovies.data.source.remote.response.movie.DetailMovieResponse
import com.rijaldev.inmovies.data.source.remote.response.movie.ResultsItemMovie
import com.rijaldev.inmovies.data.source.remote.response.tvshow.DetailTvShowResponse
import com.rijaldev.inmovies.data.source.remote.response.tvshow.ResultsItemTvShow
import com.rijaldev.inmovies.utils.AppExecutors
import com.rijaldev.inmovies.vo.Resource

class FakeMovieCatalogueRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): MovieCatalogueDataSource {

    override fun getMovies(sort: String): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<ResultsItemMovie>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(10)
                    .setPageSize(10)
                    .build()
                return LivePagedListBuilder(localDataSource.getMovies(sort), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<ResultsItemMovie>>> =
                remoteDataSource.getMovies()

            override fun saveCallResult(data: List<ResultsItemMovie>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in data) {
                    val movie = MovieEntity(
                        response.id,
                        response.title,
                        response.voteAverage,
                        response.posterPath,
                        response.language
                    )
                    movieList.add(movie)
                }
                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getDetailMovie(movieId: String): LiveData<Resource<DetailEntity>> {
        return object : NetworkBoundResource<DetailEntity, DetailMovieResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<DetailEntity> =
                localDataSource.getDetail(movieId)

            override fun shouldFetch(data: DetailEntity?): Boolean =
                data == null

            override fun createCall(): LiveData<ApiResponse<DetailMovieResponse>> =
                remoteDataSource.getDetailMovie(movieId)

            override fun saveCallResult(data: DetailMovieResponse) {
                val listGenre = ArrayList<String>()
                for (genre in data.genres) {
                    listGenre.add(genre.name)
                }
                val detailEntity = DetailEntity(
                    data.id,
                    data.title,
                    data.tagline,
                    data.overview,
                    listGenre,
                    data.releaseDate,
                    data.voteAverage,
                    data.runtime,
                    null,
                    data.posterPath,
                    data.originalLanguage,
                    data.status,
                    false,
                    "movie"
                )
                localDataSource.insertDetail(detailEntity)
            }
        }.asLiveData()
    }

    override fun getTvShows(sort: String): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object : NetworkBoundResource<PagedList<TvShowEntity>, List<ResultsItemTvShow>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<TvShowEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(10)
                    .setPageSize(10)
                    .build()
                return LivePagedListBuilder(localDataSource.getTvShows(sort), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<ResultsItemTvShow>>> =
                remoteDataSource.getTvShows()

            override fun saveCallResult(data: List<ResultsItemTvShow>) {
                val tvShowList = ArrayList<TvShowEntity>()
                for (response in data) {
                    val tvShow = TvShowEntity(
                        response.id,
                        response.name,
                        response.voteAverage,
                        response.posterPath,
                        response.originalLanguage
                    )
                    tvShowList.add(tvShow)
                }
                localDataSource.insertTvShow(tvShowList)
            }
        }.asLiveData()
    }

    override fun getDetailTvShow(tvId: String): LiveData<Resource<DetailEntity>> {
        return object : NetworkBoundResource<DetailEntity, DetailTvShowResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<DetailEntity> =
                localDataSource.getDetail(tvId)

            override fun shouldFetch(data: DetailEntity?): Boolean =
                data == null

            override fun createCall(): LiveData<ApiResponse<DetailTvShowResponse>> =
                remoteDataSource.getDetailTvShow(tvId)

            override fun saveCallResult(data: DetailTvShowResponse) {
                val listGenre = ArrayList<String>()
                for (genre in data.genres) {
                    listGenre.add(genre.name)
                }
                val detailEntity = DetailEntity(
                    data.id,
                    data.name,
                    data.tagline,
                    data.overview,
                    listGenre,
                    data.lastAirDate,
                    data.voteAverage,
                    null,
                    data.numberOfEpisodes,
                    data.posterPath,
                    data.originalLanguage,
                    data.status,
                    false,
                    "tv_show"
                )
                localDataSource.insertDetail(detailEntity)
            }
        }.asLiveData()
    }

    override fun getFavoriteTvShows(): LiveData<PagedList<DetailEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(10)
            .setPageSize(10)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteTvShows(), config).build()
    }

    override fun getFavoriteMovies(): LiveData<PagedList<DetailEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(10)
            .setPageSize(10)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovies(), config).build()
    }

    override fun setFavorite(detailEntity: DetailEntity, state: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setFavorite(detailEntity, state) }
}
package com.rijaldev.inmovies.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.rijaldev.inmovies.data.source.local.LocalDataSource
import com.rijaldev.inmovies.data.source.local.entity.DetailEntity
import com.rijaldev.inmovies.data.source.local.entity.MovieEntity
import com.rijaldev.inmovies.data.source.local.entity.TvShowEntity
import com.rijaldev.inmovies.data.source.local.room.MovieCatalogueDao
import org.mockito.Mockito.*
import com.rijaldev.inmovies.data.source.remote.RemoteDataSource
import com.rijaldev.inmovies.utils.*
import com.rijaldev.inmovies.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test

class MovieCatalogueRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)
    private val movieCatalogueDao = mock(MovieCatalogueDao::class.java)
    private val movieCatalogueRepository = FakeMovieCatalogueRepository(remote, local, appExecutors)
    private val moviesResponse = DataDummy.getMoviesResponse()
    private val tvShowsResponse = DataDummy.getTvShowsResponse()
    private val movieDetailResponse = DataDummy.getDetailMovieResponse()
    private val movieId = movieDetailResponse.id
    private val tvShowDetailResponse = DataDummy.getDetailTvShowResponse()
    private val tvShowId = tvShowDetailResponse.id
    private val detailMovie = DataDummy.getDetail()[0]
    private val detailTvShow = DataDummy.getDetail()[1]

    @Test
    fun getMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getMovies(SortUtils.DEFAULT)).thenReturn(dataSourceFactory)
        movieCatalogueRepository.getMovies(SortUtils.DEFAULT)

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.getMovies()))
        verify(local).getMovies(SortUtils.DEFAULT)
        assertNotNull(movieEntities)
        assertEquals(moviesResponse.size, movieEntities.data?.size)
    }

    @Test
    fun getTvShows() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getTvShows(SortUtils.DEFAULT)).thenReturn(dataSourceFactory)
        movieCatalogueRepository.getTvShows(SortUtils.DEFAULT)

        val tvShowEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.getTvShows()))
        verify(local).getTvShows(SortUtils.DEFAULT)
        assertNotNull(tvShowEntities)
        assertEquals(tvShowsResponse.size, tvShowEntities.data?.size)
    }

    @Test
    fun getDetailMovie() {
        val dummyMovie = MutableLiveData<DetailEntity>()
        dummyMovie.value = detailMovie
        `when`(local.getDetail(movieId.toString())).thenReturn(dummyMovie)

        val movieEntities = LiveDataTestUtil.getValue(movieCatalogueRepository.getDetailMovie(movieId.toString()))
        verify(local).getDetail(movieId.toString())
        assertNotNull(movieEntities)
        assertEquals(detailMovie.id, movieEntities.data?.id)
    }

    @Test
    fun getDetailTvShow() {
        val dummyTvShow = MutableLiveData<DetailEntity>()
        dummyTvShow.value = detailTvShow
        `when`(local.getDetail(tvShowId.toString())).thenReturn(dummyTvShow)

        val tvShowEntities = LiveDataTestUtil.getValue(movieCatalogueRepository.getDetailTvShow(tvShowId.toString()))
        verify(local).getDetail(tvShowId.toString())
        assertNotNull(tvShowEntities)
        assertEquals(detailTvShow.id, tvShowEntities.data?.id)
    }

    @Test
    fun getFavoriteTvShows() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, DetailEntity>
        `when`(local.getFavoriteTvShows()).thenReturn(dataSourceFactory)
        movieCatalogueRepository.getFavoriteTvShows()

        val tvShowEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.getTvShows()))
        verify(local).getFavoriteTvShows()
        assertNotNull(tvShowEntities)
        assertEquals(tvShowsResponse.size, tvShowEntities.data?.size)
    }

    @Test
    fun getFavoriteMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, DetailEntity>
        `when`(local.getFavoriteMovies()).thenReturn(dataSourceFactory)
        movieCatalogueRepository.getFavoriteMovies()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.getMovies()))
        verify(local).getFavoriteMovies()
        assertNotNull(movieEntities)
        assertEquals(moviesResponse.size, movieEntities.data?.size)
    }

    @Test
    fun setFavorite() {
        val localDataSource = LocalDataSource(movieCatalogueDao)
        val dummyDetail = DataDummy.getDetail()[0]
        val resultDetail = dummyDetail.copy(isFavorite = true)
        val testExecutors = AppExecutors(AppExecutorsTestUtil(), AppExecutorsTestUtil())

        `when`(appExecutors.diskIO()).thenReturn(testExecutors.diskIO())
        doNothing().`when`(local).setFavorite(dummyDetail, true)
        movieCatalogueRepository.setFavorite(dummyDetail, true)
        verify(local).setFavorite(dummyDetail, true)

        doNothing().`when`(movieCatalogueDao).update(dummyDetail)
        localDataSource.setFavorite(dummyDetail, true)
        verify(movieCatalogueDao, times(1)).update(resultDetail)
    }
}
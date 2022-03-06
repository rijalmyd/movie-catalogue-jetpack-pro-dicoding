package com.rijaldev.inmovies.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.rijaldev.inmovies.data.source.local.entity.DetailEntity
import com.rijaldev.inmovies.data.source.MovieCatalogueRepository
import com.rijaldev.inmovies.utils.DataDummy
import com.rijaldev.inmovies.vo.Resource
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel

    private val date = "2022-02-20"
    private val dummyMovie = DataDummy.getDetail()[0]
    private val dummyTvShow = DataDummy.getDetail()[1]
    private val movieId = dummyMovie.id.toString()
    private val tvShowId = dummyTvShow.id.toString()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieCatalogueRepository: MovieCatalogueRepository

    @Mock
    private lateinit var detailObserver: Observer<Resource<DetailEntity>>

    @Before
    fun setUp() {
        viewModel = DetailViewModel(movieCatalogueRepository)
        viewModel.setMovieId(movieId)
        viewModel.setTvShowId(tvShowId)
        viewModel.setDate(date)
    }

    @Test
    fun getMovie() {
        val movies = MutableLiveData<Resource<DetailEntity>>()
        movies.value = Resource.success(dummyMovie)

        `when`(movieCatalogueRepository.getDetailMovie(movieId)).thenReturn(movies)
        viewModel.setMovieId(movieId)
        viewModel.movieDetail.observeForever(detailObserver)
        val movieEntity = viewModel.movieDetail.value?.data
        verify(movieCatalogueRepository).getDetailMovie(movieId)

        assertNotNull(movieEntity)
        assertEquals(dummyMovie.id, movieEntity?.id)
        assertEquals(dummyMovie.title, movieEntity?.title)
        assertEquals(dummyMovie.userScore, movieEntity?.userScore as Double, 0.0001)
        assertEquals(dummyMovie.image, movieEntity.image)
        assertEquals(dummyMovie.dateRelease, movieEntity.dateRelease)
        assertEquals(dummyMovie.tagline, movieEntity.tagline)
        assertEquals(dummyMovie.description, movieEntity.description)
        assertEquals(dummyMovie.genre, movieEntity.genre)
        assertEquals(dummyMovie.runtime, movieEntity.runtime)
        assertEquals(dummyMovie.episodes, movieEntity.episodes)
        assertEquals(dummyMovie.speechLanguage, movieEntity.speechLanguage)
        assertEquals(dummyMovie.status, movieEntity.status)

        verify(detailObserver).onChanged(Resource.success(dummyMovie))
    }

    @Test
    fun getTvShow() {
        val tvShows = MutableLiveData<Resource<DetailEntity>>()
        tvShows.value = Resource.success(dummyTvShow)

        `when`(movieCatalogueRepository.getDetailTvShow(tvShowId)).thenReturn(tvShows)
        viewModel.setTvShowId(tvShowId)
        viewModel.tvShowDetail.observeForever(detailObserver)
        val tvShowEntity = viewModel.tvShowDetail.value?.data
        verify(movieCatalogueRepository).getDetailTvShow(tvShowId)

        assertNotNull(tvShowEntity)
        assertEquals(dummyTvShow.id, tvShowEntity?.id)
        assertEquals(dummyTvShow.title, tvShowEntity?.title)
        assertEquals(dummyTvShow.genre, tvShowEntity?.genre)
        assertEquals(dummyTvShow.image, tvShowEntity?.image)
        assertEquals(dummyTvShow.userScore, tvShowEntity?.userScore as Double, 0.0001)
        assertEquals(dummyTvShow.tagline, tvShowEntity.tagline)
        assertEquals(dummyTvShow.description, tvShowEntity.description)
        assertEquals(dummyTvShow.runtime, tvShowEntity.runtime)
        assertEquals(dummyTvShow.episodes, tvShowEntity.episodes)
        assertEquals(dummyTvShow.speechLanguage, tvShowEntity.speechLanguage)
        assertEquals(dummyTvShow.status, tvShowEntity.status)

        verify(detailObserver).onChanged(Resource.success(dummyTvShow))
    }

    @Test
    fun getDate() {
        viewModel.setDate(date)
        assertNotNull(viewModel.getDate())
        assertEquals("20 Feb 2022", viewModel.getDate())
    }

    @Test
    fun getYear() {
        viewModel.setDateToYear(date)
        assertNotNull(viewModel.getYear())
        assertEquals("2022", viewModel.getYear())
    }
}
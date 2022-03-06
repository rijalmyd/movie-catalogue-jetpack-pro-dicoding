package com.rijaldev.inmovies.ui.favorite.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.verify
import com.rijaldev.inmovies.data.source.MovieCatalogueRepository
import com.rijaldev.inmovies.data.source.local.entity.DetailEntity
import com.rijaldev.inmovies.utils.DataDummy
import com.rijaldev.inmovies.utils.PagedListUtil
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class FavoriteMovieViewModelTest {
    private lateinit var viewModel: FavoriteMovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieCatalogueRepository: MovieCatalogueRepository

    @Mock
    private lateinit var observer: Observer<PagedList<DetailEntity>>

    @Before
    fun setUp() {
        viewModel = FavoriteMovieViewModel(movieCatalogueRepository)
    }

    @Test
    fun getFavoriteTvShow() {
        val dummyFavMovie = PagedListUtil.mockPagedList(DataDummy.getDetail())
        val detailMovie = MutableLiveData<PagedList<DetailEntity>>()
        detailMovie.value = dummyFavMovie

        Mockito.`when`(movieCatalogueRepository.getFavoriteMovies()).thenReturn(detailMovie)
        val movieEntities = viewModel.getFavoriteMovies().value
        verify(movieCatalogueRepository).getFavoriteMovies()
        assertNotNull(movieEntities)
        assertEquals(2, movieEntities?.size)

        viewModel.getFavoriteMovies().observeForever(observer)
        verify(observer).onChanged(dummyFavMovie)
    }
}
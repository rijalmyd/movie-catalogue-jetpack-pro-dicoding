package com.rijaldev.inmovies.ui.dashboard.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.verify
import com.rijaldev.inmovies.data.source.local.entity.MovieEntity
import com.rijaldev.inmovies.data.source.MovieCatalogueRepository
import com.rijaldev.inmovies.utils.DataDummy
import com.rijaldev.inmovies.utils.PagedListUtil
import com.rijaldev.inmovies.utils.SortUtils
import com.rijaldev.inmovies.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class MovieViewModelTest{

    private lateinit var viewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieCatalogueRepository: MovieCatalogueRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MovieEntity>>>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(movieCatalogueRepository)
    }

    @Test
    fun testGetMovies() {
        val dummyMovies = Resource.success(PagedListUtil.mockPagedList(DataDummy.getMovies()))
        val movies = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        movies.value = dummyMovies

        `when`(movieCatalogueRepository.getMovies(SortUtils.DEFAULT)).thenReturn(movies)
        val movieEntities = viewModel.getMovies(SortUtils.DEFAULT).value?.data
        verify(movieCatalogueRepository).getMovies(SortUtils.DEFAULT)
        assertNotNull(movieEntities)
        assertEquals(2, movieEntities?.size)

        viewModel.getMovies(SortUtils.DEFAULT).observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }
}
package com.rijaldev.inmovies.ui.dashboard.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.verify
import com.rijaldev.inmovies.data.source.local.entity.TvShowEntity
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
class TvShowViewModelTest{

    private lateinit var viewModel: TvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieCatalogueRepository: MovieCatalogueRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<TvShowEntity>>>

    @Before
    fun setUp() {
        viewModel = TvShowViewModel(movieCatalogueRepository)
    }

    @Test
    fun testGetTvShows() {
        val dummyTvShows = Resource.success(PagedListUtil.mockPagedList(DataDummy.getTvShows()))
        val tvShows = MutableLiveData<Resource<PagedList<TvShowEntity>>>()
        tvShows.value = dummyTvShows

        `when`(movieCatalogueRepository.getTvShows(SortUtils.DEFAULT)).thenReturn(tvShows)
        val tvShowEntities = viewModel.getTvShows(SortUtils.DEFAULT).value?.data
        verify(movieCatalogueRepository).getTvShows(SortUtils.DEFAULT)
        assertNotNull(tvShowEntities)
        assertEquals(2, tvShowEntities?.size)

        viewModel.getTvShows(SortUtils.DEFAULT).observeForever(observer)
        verify(observer).onChanged(dummyTvShows)
    }
}
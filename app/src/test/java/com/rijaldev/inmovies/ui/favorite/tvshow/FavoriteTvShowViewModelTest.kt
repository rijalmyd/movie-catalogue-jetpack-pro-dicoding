package com.rijaldev.inmovies.ui.favorite.tvshow

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
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class FavoriteTvShowViewModelTest {
    private lateinit var viewModel: FavoriteTvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieCatalogueRepository: MovieCatalogueRepository

    @Mock
    private lateinit var observer: Observer<PagedList<DetailEntity>>

    @Before
    fun setUp() {
        viewModel = FavoriteTvShowViewModel(movieCatalogueRepository)
    }

    @Test
    fun getFavoriteTvShow() {
        val dummyFavTv = PagedListUtil.mockPagedList(DataDummy.getDetail())
        val detailTvShow = MutableLiveData<PagedList<DetailEntity>>()
        detailTvShow.value = dummyFavTv

        `when`(movieCatalogueRepository.getFavoriteTvShows()).thenReturn(detailTvShow)
        val tvShowEntities = viewModel.getFavoriteTvShows().value
        verify(movieCatalogueRepository).getFavoriteTvShows()
        assertNotNull(tvShowEntities)
        assertEquals(2, tvShowEntities?.size)

        viewModel.getFavoriteTvShows().observeForever(observer)
        verify(observer).onChanged(dummyFavTv)
    }
}
package com.rijaldev.inmovies.ui.dashboard.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.rijaldev.inmovies.data.source.MovieCatalogueRepository
import com.rijaldev.inmovies.data.source.local.entity.TvShowEntity
import com.rijaldev.inmovies.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(private val movieCatalogueRepository: MovieCatalogueRepository): ViewModel() {

    fun getTvShows(sort: String): LiveData<Resource<PagedList<TvShowEntity>>> = movieCatalogueRepository.getTvShows(sort)
}
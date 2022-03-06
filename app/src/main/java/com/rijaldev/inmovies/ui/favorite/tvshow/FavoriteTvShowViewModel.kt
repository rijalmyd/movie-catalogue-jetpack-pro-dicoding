package com.rijaldev.inmovies.ui.favorite.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.rijaldev.inmovies.data.source.MovieCatalogueRepository
import com.rijaldev.inmovies.data.source.local.entity.DetailEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteTvShowViewModel @Inject constructor(private val movieCatalogueRepository: MovieCatalogueRepository): ViewModel() {

    fun getFavoriteTvShows(): LiveData<PagedList<DetailEntity>> = movieCatalogueRepository.getFavoriteTvShows()
}
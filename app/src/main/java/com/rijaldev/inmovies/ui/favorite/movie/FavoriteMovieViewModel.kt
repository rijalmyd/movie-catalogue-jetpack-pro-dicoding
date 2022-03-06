package com.rijaldev.inmovies.ui.favorite.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.rijaldev.inmovies.data.source.MovieCatalogueRepository
import com.rijaldev.inmovies.data.source.local.entity.DetailEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteMovieViewModel @Inject constructor(private val movieCatalogueRepository: MovieCatalogueRepository): ViewModel() {

    fun getFavoriteMovies(): LiveData<PagedList<DetailEntity>> = movieCatalogueRepository.getFavoriteMovies()
}
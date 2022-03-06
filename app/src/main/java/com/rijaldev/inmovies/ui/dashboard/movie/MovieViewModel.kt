package com.rijaldev.inmovies.ui.dashboard.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.rijaldev.inmovies.data.source.local.entity.MovieEntity
import com.rijaldev.inmovies.data.source.MovieCatalogueRepository
import com.rijaldev.inmovies.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieCatalogueRepository: MovieCatalogueRepository): ViewModel() {

    fun getMovies(sort: String): LiveData<Resource<PagedList<MovieEntity>>> = movieCatalogueRepository.getMovies(sort)
}
package com.rijaldev.inmovies.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.rijaldev.inmovies.data.source.local.entity.DetailEntity
import com.rijaldev.inmovies.data.source.MovieCatalogueRepository
import com.rijaldev.inmovies.utils.DateFormatChanger
import com.rijaldev.inmovies.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val movieCatalogueRepository: MovieCatalogueRepository): ViewModel() {

    private val movieId = MutableLiveData<String>()
    private val tvShowId = MutableLiveData<String>()
    private var newDate: String? = null
    private var dateYear: String? = null

    fun setMovieId(movieId: String) {
        this.movieId.value = movieId
    }

    fun setTvShowId(tvShowId: String) {
        this.tvShowId.value = tvShowId
    }

    var movieDetail: LiveData<Resource<DetailEntity>> = Transformations.switchMap(movieId) { mId ->
        movieCatalogueRepository.getDetailMovie(mId)
    }

    var tvShowDetail: LiveData<Resource<DetailEntity>> = Transformations.switchMap(tvShowId) { tvId ->
        movieCatalogueRepository.getDetailTvShow(tvId)
    }

    fun setFavoriteMovie() {
        val detailResource = movieDetail.value
        if (detailResource != null) {
            val resource = detailResource.data
            if (resource != null) {
                val newState = !resource.isFavorite
                movieCatalogueRepository.setFavorite(resource, newState)
            }
        }
    }

    fun setFavoriteTvShow() {
        val detailResource = tvShowDetail.value
        if (detailResource != null) {
            val resource = detailResource.data
            if (resource != null) {
                val newState = !resource.isFavorite
                movieCatalogueRepository.setFavorite(resource, newState)
            }
        }
    }

    fun getDate(): String? = newDate

    fun getYear(): String? = dateYear

    fun setDate(date: String) {
        this.newDate = DateFormatChanger.toOtherDate(date)
    }

    fun setDateToYear(date: String) {
        this.dateYear = DateFormatChanger.toOnlyYear(date)
    }
}
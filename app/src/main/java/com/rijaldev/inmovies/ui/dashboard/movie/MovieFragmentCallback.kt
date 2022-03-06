package com.rijaldev.inmovies.ui.dashboard.movie

import com.rijaldev.inmovies.data.source.local.entity.MovieEntity

interface MovieFragmentCallback {

    fun onMenuClick(movie: MovieEntity)
}
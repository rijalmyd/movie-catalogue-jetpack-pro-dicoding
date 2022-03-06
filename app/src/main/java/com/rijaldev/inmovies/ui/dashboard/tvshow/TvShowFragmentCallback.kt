package com.rijaldev.inmovies.ui.dashboard.tvshow

import com.rijaldev.inmovies.data.source.local.entity.TvShowEntity

interface TvShowFragmentCallback {

    fun onMenuClick(tvShow: TvShowEntity)
}
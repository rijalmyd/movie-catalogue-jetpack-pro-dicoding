package com.rijaldev.inmovies.ui.favorite

import com.rijaldev.inmovies.data.source.local.entity.DetailEntity

interface FavoriteCallback {
    fun onShareClick(detailEntity: DetailEntity)
}
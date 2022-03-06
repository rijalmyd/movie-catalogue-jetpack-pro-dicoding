package com.rijaldev.inmovies.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rijaldev.inmovies.data.source.local.entity.DetailEntity
import com.rijaldev.inmovies.data.source.local.entity.MovieEntity
import com.rijaldev.inmovies.data.source.local.entity.TvShowEntity
import com.rijaldev.inmovies.utils.StringListConverter

@Database(entities = [
    DetailEntity::class,
    MovieEntity::class,
    TvShowEntity::class], version = 1, exportSchema = false)
@TypeConverters(StringListConverter::class)
abstract class MovieCatalogueDatabase: RoomDatabase() {
    abstract fun movieCatalogueDao(): MovieCatalogueDao
}
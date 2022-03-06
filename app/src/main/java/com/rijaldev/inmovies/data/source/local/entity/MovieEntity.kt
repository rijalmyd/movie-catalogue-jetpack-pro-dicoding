package com.rijaldev.inmovies.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movie_entities")
data class MovieEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movie_id")
    var movieId: Int,

    @NonNull
    @ColumnInfo(name = "title")
    var title: String,

    @NonNull
    @ColumnInfo(name = "user_score")
    var userScore: Double,

    @NonNull
    @ColumnInfo(name = "image")
    var image: String,

    @NonNull
    @ColumnInfo(name = "language")
    var language: String
): Parcelable

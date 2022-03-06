package com.rijaldev.inmovies.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Entity(tableName = "detail_entities")
@Parcelize
data class DetailEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int,

    @NonNull
    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "tagline")
    var tagline: String? = null,

    @ColumnInfo(name = "description")
    var description: String? = null,

    @NonNull
    @ColumnInfo(name = "genre")
    var genre: List<String>,

    @NonNull
    @ColumnInfo(name = "date_release")
    var dateRelease: String,

    @NonNull
    @ColumnInfo(name = "user_score")
    var userScore: Double,

    @ColumnInfo(name = "runtime")
    var runtime: Int? = null,

    @ColumnInfo(name = "episodes")
    var episodes: Int? = null,

    @NonNull
    @ColumnInfo(name = "image")
    var image: String,

    @NonNull
    @ColumnInfo(name = "speech_language")
    var speechLanguage: String,

    @NonNull
    @ColumnInfo(name = "status")
    var status: String,

    @NonNull
    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false,

    @NonNull
    @ColumnInfo(name = "detail_type")
    var detailType: String
): Parcelable
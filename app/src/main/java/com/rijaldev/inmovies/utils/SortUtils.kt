package com.rijaldev.inmovies.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {
    const val ASC = "Asc"
    const val DESC = "Desc"
    const val RATING = "Rating"
    const val DEFAULT ="Default"

    fun getSortedQueryMovies(filter: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM movie_entities")
        when (filter) {
            ASC -> simpleQuery.append(" ORDER BY title ASC")
            DESC -> simpleQuery.append(" ORDER BY title DESC")
            RATING -> simpleQuery.append(" ORDER BY user_score DESC")
            DEFAULT -> simpleQuery.append("")
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }

    fun getSortedQueryTvShows(filter: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM tv_show_entities")
        when (filter) {
            ASC -> simpleQuery.append(" ORDER BY title ASC")
            DESC -> simpleQuery.append(" ORDER BY title DESC")
            RATING -> simpleQuery.append(" ORDER BY user_score DESC")
            DEFAULT -> simpleQuery.append("")
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}
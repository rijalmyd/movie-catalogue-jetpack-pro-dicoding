package com.rijaldev.inmovies.utils

import com.rijaldev.inmovies.data.source.local.entity.DetailEntity
import com.rijaldev.inmovies.data.source.local.entity.MovieEntity
import com.rijaldev.inmovies.data.source.local.entity.TvShowEntity
import com.rijaldev.inmovies.data.source.remote.response.movie.DetailMovieResponse
import com.rijaldev.inmovies.data.source.remote.response.movie.GenresItem
import com.rijaldev.inmovies.data.source.remote.response.movie.ResultsItemMovie
import com.rijaldev.inmovies.data.source.remote.response.tvshow.DetailTvShowResponse
import com.rijaldev.inmovies.data.source.remote.response.tvshow.ResultsItemTvShow

object DataDummy {

    fun getMovies(): List<MovieEntity> {
        return listOf(
            MovieEntity(
                634649,
                "Spider-Man: No Way Home",
                8.3,
                "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
                "en"
            ),
            MovieEntity(
                476669,
                "The King's Man",
                7.1,
                "/aq4Pwv5Xeuvj6HZKtxyd23e6bE9.jpg",
                "en"
            )
        )
    }

    fun getTvShows(): List<TvShowEntity> {
        return listOf(
            TvShowEntity(
                85552,
                "Euphoria",
                8.4,
                "/jtnfNzqZwN4E32FGGxx1YZaBWWf.jpg",
                "en"
            ),
            TvShowEntity(
                99966,
                "All of Us Are Dead",
                8.7,
                "/ze4lhw0oLBHYmlM2KuZjBg0Sq6H.jpg",
                "ko"
            )
        )
    }

    fun getDetail(): List<DetailEntity> {
        return listOf(
            DetailEntity(
                634649,
                "Spider-Man: No Way Home",
                "The Multiverse unleashed.",
                "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
                listOf("Action", "Adventure", "Science Fiction"),
                "2021-12-15",
                8.3,
                148,
                null,
                "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
                "en",
                "Released",
                false,
                "movie"
            ),
            DetailEntity(
                85552,
                "Euphoria",
                "Remember this feeling.",
                "A group of high school students navigate love and friendships in a world of drugs, sex, trauma, and social media.",
                listOf("Drama"),
                "2022-02-20",
                8.4,
                null,
                16,
                "/jtnfNzqZwN4E32FGGxx1YZaBWWf.jpg",
                "en",
                "Returning Series",
                false,
                "tv_show"
            )
        )
    }

    fun getMoviesResponse(): List<ResultsItemMovie> {
        return listOf(
            ResultsItemMovie(
                8.3,
                634649,
                "Spider-Man: No Way Home",
                "en",
                "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg"
            ),
            ResultsItemMovie(
                7.1,
                476669,
                "The King's Man",
                "en",
                "/aq4Pwv5Xeuvj6HZKtxyd23e6bE9.jpg"
            )
        )
    }

    fun getTvShowsResponse(): List<ResultsItemTvShow> {
        return listOf(
            ResultsItemTvShow(
                "en",
                8.4,
                85552,
                "Euphoria",
                "/jtnfNzqZwN4E32FGGxx1YZaBWWf.jpg"
            ),
            ResultsItemTvShow(
                "ko",
                8.7,
                99966,
                "All of Us Are Dead",
                "/ze4lhw0oLBHYmlM2KuZjBg0Sq6H.jpg"
            )
        )
    }

    fun getDetailMovieResponse(): DetailMovieResponse {
        return DetailMovieResponse(
            "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
            "en",
            "2021-12-15",
            listOf(GenresItem("Action"), GenresItem("Adventure"), GenresItem("Science Fiction")),
            8.3,
            148,
            "The Multiverse unleashed.",
            634649,
            "Spider-Man: No Way Home",
            "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
            "Released"
        )
    }

    fun getDetailTvShowResponse(): DetailTvShowResponse {
        return DetailTvShowResponse(
            16,
            "A group of high school students navigate love and friendships in a world of drugs, sex, trauma, and social media.",
            "en",
            listOf(com.rijaldev.inmovies.data.source.remote.response.tvshow.GenresItem("Drama")),
            8.4,
            "Euphoria",
            "Remember this feeling.",
            85552,
            "2022-02-20",
            "/jtnfNzqZwN4E32FGGxx1YZaBWWf.jpg",
            "Returning Series",
        )
    }
}
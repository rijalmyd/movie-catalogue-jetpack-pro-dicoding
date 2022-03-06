package com.rijaldev.inmovies.data.source.remote.response.movie

import com.google.gson.annotations.SerializedName

data class MoviesResponse(

	@field:SerializedName("results")
	val results: List<ResultsItemMovie>,
)

data class ResultsItemMovie(

	@field:SerializedName("vote_average")
	val voteAverage: Double,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("original_language")
	val language: String,

	@field:SerializedName("poster_path")
	val posterPath: String
)

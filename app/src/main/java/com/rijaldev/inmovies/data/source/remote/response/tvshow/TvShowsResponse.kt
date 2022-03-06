package com.rijaldev.inmovies.data.source.remote.response.tvshow

import com.google.gson.annotations.SerializedName

data class TvShowsResponse(

	@field:SerializedName("results")
	val results: List<ResultsItemTvShow>
)

data class ResultsItemTvShow(

	@field:SerializedName("original_language")
	val originalLanguage: String,

	@field:SerializedName("vote_average")
	val voteAverage: Double,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("poster_path")
	val posterPath: String
)

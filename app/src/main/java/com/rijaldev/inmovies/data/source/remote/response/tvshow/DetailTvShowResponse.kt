package com.rijaldev.inmovies.data.source.remote.response.tvshow

import com.google.gson.annotations.SerializedName

data class DetailTvShowResponse(

	@field:SerializedName("number_of_episodes")
	val numberOfEpisodes: Int,

	@field:SerializedName("overview")
	val overview: String,

	@field:SerializedName("original_language")
	val originalLanguage: String,

	@field:SerializedName("genres")
	val genres: List<GenresItem>,

	@field:SerializedName("vote_average")
	val voteAverage: Double,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("tagline")
	val tagline: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("last_air_date")
	val lastAirDate: String,

	@field:SerializedName("poster_path")
	val posterPath: String,

	@field:SerializedName("status")
	val status: String
)

data class GenresItem(

	@field:SerializedName("name")
	val name: String,
)

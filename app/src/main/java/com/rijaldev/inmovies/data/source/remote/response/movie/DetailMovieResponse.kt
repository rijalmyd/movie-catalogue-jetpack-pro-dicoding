package com.rijaldev.inmovies.data.source.remote.response.movie

import com.google.gson.annotations.SerializedName

data class DetailMovieResponse(

	@field:SerializedName("overview")
	val overview: String,

	@field:SerializedName("original_language")
	val originalLanguage: String,

	@field:SerializedName("release_date")
	val releaseDate: String,

	@field:SerializedName("genres")
	val genres: List<GenresItem>,

	@field:SerializedName("vote_average")
	val voteAverage: Double,

	@field:SerializedName("runtime")
	val runtime: Int,

	@field:SerializedName("tagline")
	val tagline: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("poster_path")
	val posterPath: String,

	@field:SerializedName("status")
	val status: String
)

data class GenresItem(

	@field:SerializedName("name")
	val name: String,
)

package com.rijaldev.inmovies.ui.dashboard.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.rijaldev.inmovies.BuildConfig
import com.rijaldev.inmovies.R
import com.rijaldev.inmovies.data.source.local.entity.MovieEntity
import com.rijaldev.inmovies.databinding.ItemsMovieBinding
import com.rijaldev.inmovies.ui.detail.DetailActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieAdapter(private val callback: MovieFragmentCallback): PagedListAdapter<MovieEntity, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemsMovieBinding = ItemsMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemsMovieBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }

    inner class MovieViewHolder(private val binding: ItemsMovieBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity) {
            with(binding) {
                tvTitle.text = movie.title
                tvLanguage.text = movie.language
                tvRating.text = itemView.resources.getString(R.string.rating, movie.userScore.toString())
                imageMenu.setOnClickListener { callback.onMenuClick(movie) }

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                        .putExtra(DetailActivity.EXTRA_ID, movie.movieId.toString())
                        .putExtra(DetailActivity.EXTRA_TYPE, DetailActivity.TYPE_MOVIE)
                    itemView.context.startActivity(intent)
                }

                CoroutineScope(Dispatchers.Main).launch {
                    Glide.with(itemView.context)
                        .load(BuildConfig.IMAGE_URL + movie.image)
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                        .error(R.drawable.ic_error)
                        .transform(RoundedCorners(8))
                        .into(ivPoster)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean =
                oldItem.movieId == newItem.movieId

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean =
                oldItem == newItem
        }
    }
}
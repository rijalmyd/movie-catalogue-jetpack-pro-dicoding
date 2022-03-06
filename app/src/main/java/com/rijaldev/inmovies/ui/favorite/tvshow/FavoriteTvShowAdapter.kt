package com.rijaldev.inmovies.ui.favorite.tvshow

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
import com.rijaldev.inmovies.data.source.local.entity.DetailEntity
import com.rijaldev.inmovies.databinding.ItemsTvShowBinding
import com.rijaldev.inmovies.ui.detail.DetailActivity
import com.rijaldev.inmovies.ui.favorite.FavoriteCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteTvShowAdapter(private val callback: FavoriteCallback): PagedListAdapter<DetailEntity, FavoriteTvShowAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteTvShowAdapter.ViewHolder {
        val binding = ItemsTvShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteTvShowAdapter.ViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null) holder.bind(tvShow)
    }

    inner class ViewHolder(private val binding: ItemsTvShowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: DetailEntity) {
            with(binding) {
                tvTitle.text = tvShow.title
                tvLanguage.text = tvShow.speechLanguage
                tvRating.text = itemView.resources.getString(R.string.rating, tvShow.userScore.toString())
                imageMenu.setOnClickListener { callback.onShareClick(tvShow) }

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                        .putExtra(DetailActivity.EXTRA_ID, tvShow.id.toString())
                        .putExtra(DetailActivity.EXTRA_TYPE, DetailActivity.TYPE_TVSHOW)
                    itemView.context.startActivity(intent)
                }

                CoroutineScope(Dispatchers.Main).launch {
                    Glide.with(itemView.context)
                        .load(BuildConfig.IMAGE_URL + tvShow.image)
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                        .error(R.drawable.ic_error)
                        .transform(RoundedCorners(8))
                        .into(ivPoster)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DetailEntity>() {
            override fun areItemsTheSame(oldItem: DetailEntity, newItem: DetailEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: DetailEntity, newItem: DetailEntity): Boolean =
                oldItem == newItem
        }
    }
}
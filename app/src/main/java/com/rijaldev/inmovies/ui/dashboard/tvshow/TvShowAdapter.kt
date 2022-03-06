package com.rijaldev.inmovies.ui.dashboard.tvshow

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
import com.rijaldev.inmovies.data.source.local.entity.TvShowEntity
import com.rijaldev.inmovies.databinding.ItemsTvShowBinding
import com.rijaldev.inmovies.ui.detail.DetailActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TvShowAdapter(private val callback: TvShowFragmentCallback): PagedListAdapter<TvShowEntity, TvShowAdapter.TvViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val binding = ItemsTvShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null) {
            holder.bind(tvShow)
        }
    }

    inner class TvViewHolder(private val binding: ItemsTvShowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShowEntity) {
            with(binding) {
                tvTitle.text = tvShow.title
                tvLanguage.text = tvShow.language
                tvRating.text = itemView.resources.getString(R.string.rating, tvShow.userScore.toString())
                imageMenu.setOnClickListener {callback.onMenuClick(tvShow)}
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                        .putExtra(DetailActivity.EXTRA_ID, tvShow.tvId.toString())
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
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowEntity>() {
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean =
                oldItem.tvId == newItem.tvId

            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean =
                oldItem == newItem
        }
    }
}
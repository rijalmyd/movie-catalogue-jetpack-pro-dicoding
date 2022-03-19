package com.rijaldev.inmovies.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.rijaldev.inmovies.BuildConfig
import com.rijaldev.inmovies.R
import com.rijaldev.inmovies.data.source.local.entity.DetailEntity
import com.rijaldev.inmovies.databinding.ActivityDetailBinding
import com.rijaldev.inmovies.databinding.ContentDetailBinding
import com.rijaldev.inmovies.utils.ViewVisibilityUtils.setViewGone
import com.rijaldev.inmovies.utils.ViewVisibilityUtils.setViewInvisible
import com.rijaldev.inmovies.utils.ViewVisibilityUtils.setViewVisible
import com.rijaldev.inmovies.vo.Resource
import com.rijaldev.inmovies.vo.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var contentDetailBinding: ContentDetailBinding
    private lateinit var activityDetailBinding: ActivityDetailBinding
    private var movieTitle: String? = null
    private var tvShowTitle: String? = null
    private val viewModel: DetailViewModel by viewModels()
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        contentDetailBinding = activityDetailBinding.contentDetail
        setContentView(activityDetailBinding.root)

        setSupportActionBar(activityDetailBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setUpView()
    }

    private fun setUpView() {
        val extras = intent?.extras
        if (extras != null) {
            val type = extras.getString(EXTRA_TYPE)
            when {
                type.equals(TYPE_MOVIE, true) -> {
                    setUpToolbarTitle(resources.getString(R.string.movies))
                    val movieId = extras.getString(EXTRA_ID)
                    if (movieId != null) {
                        viewModel.setMovieId(movieId)
                        setViewInvisible(contentDetailBinding.root)
                        viewModel.movieDetail.observe(this, movieObserver(TYPE_MOVIE))
                    }
                }
                type.equals(TYPE_TVSHOW, true) -> {
                    setUpToolbarTitle(resources.getString(R.string.tv_shows))
                    val tvShowId = extras.getString(EXTRA_ID)
                    if (tvShowId != null) {
                        viewModel.setTvShowId(tvShowId)
                        setViewInvisible(contentDetailBinding.root)
                        viewModel.tvShowDetail.observe(this, movieObserver(TYPE_TVSHOW))
                    }
                }
            }
        }
    }

    private fun movieObserver(type: String) = Observer<Resource<DetailEntity>> { listDetail ->
        if (listDetail != null) {
            when (listDetail.status) {
                Status.LOADING -> {
                    activityDetailBinding.apply {
                        setViewVisible(shimmer)
                        setViewGone(ivNotFound)
                    }
                }
                Status.SUCCESS -> {
                    activityDetailBinding.apply {
                        setViewVisible(contentDetail.root)
                        setViewGone(shimmer)
                    }
                    populateDetail(listDetail.data as DetailEntity, type)
                    val state = listDetail.data.isFavorite
                    setFavoriteState(state)
                }
                Status.ERROR -> {
                    activityDetailBinding.apply {
                        setViewGone(contentDetail.root, shimmer)
                        setViewVisible(ivNotFound)
                    }
                    Toast.makeText(this, "Terjadi Kesalahan!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun populateDetail(detailEntity: DetailEntity, type: String) {
        movieTitle = detailEntity.title
        tvShowTitle = detailEntity.title
        viewModel.setDateToYear(detailEntity.dateRelease)
        viewModel.setDate(detailEntity.dateRelease)
        val newDate = viewModel.getDate()
        val year = viewModel.getYear()
        val genreText = detailEntity.genre.toString().replace("[", "").replace("]", "")

        contentDetailBinding.apply {
            tvTitle.text = String.format(resources.getString(R.string.title), detailEntity.title, year)
            tvRating.text = resources.getString(R.string.rating_detail, detailEntity.userScore.toString())
            tvGenre.text = genreText
            tvDescription.text = detailEntity.description
            tvStatus.text = detailEntity.status
            tvLanguage.text = detailEntity.speechLanguage
            imagePoster.loadImage(BuildConfig.IMAGE_URL + detailEntity.image)

            if(type == TYPE_MOVIE) {
                tvDuration.text = resources.getString(R.string.duration, detailEntity.runtime.toString())
                tvDateRelease.text = newDate
            } else {
                tvDuration.text = resources.getString(R.string.episodes, detailEntity.episodes.toString())
                setViewGone(dateContainer)
            }

            val tagline = detailEntity.tagline
            tvTagline.apply {
                text = if (tagline != null && tagline != "") detailEntity.tagline else "-"
            }

            tvGenre.setOnClickListener {
                showSnackBar(genreText)
            }
        }
    }

    private fun setUpToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    private fun showSnackBar(genre: String) {
        val snackBar = Snackbar.make(activityDetailBinding.root, genre, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(this, R.color.blue_500))
            .setTextColor(ContextCompat.getColor(this, R.color.white))
        snackBar.show()
    }

    private fun ImageView.loadImage(url: String?) {
        Glide.with(this)
            .load(url)
            .transform(RoundedCorners(16))
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
            .error(R.drawable.ic_error)
            .into(this)
    }

    private fun onShareClicked() {
        val extras = intent?.extras
        if (extras != null) {
            val type = extras.getString(EXTRA_TYPE)
            when {
                type.equals(TYPE_MOVIE, ignoreCase = true) -> {
                    if (movieTitle != null) setShareTitle(movieTitle) else setShareTitle()
                }
                type.equals(TYPE_TVSHOW, ignoreCase = true) -> {
                    if (tvShowTitle != null) setShareTitle(tvShowTitle) else setShareTitle()
                }
            }
        }
    }

    private fun setShareTitle(title: String? = "Film dan Tv Show") {
        val mimeType = "text/plain"
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.text_share, title))
            type = mimeType
        }
        val shareIntent = Intent.createChooser(sendIntent, "Bagikan aplikasi ini sekarang")
        startActivity(shareIntent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_share -> {
                onShareClicked()
                true
            }
            R.id.btn_add_favorite -> {
                val extras = intent?.extras
                if (extras != null) {
                    val type = extras.getString(EXTRA_TYPE)
                    if (type != null) {
                        if (type == TYPE_MOVIE) {
                            viewModel.setFavoriteMovie()
                        } else if (type == TYPE_TVSHOW) {
                            viewModel.setFavoriteTvShow()
                        }
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setFavoriteState(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.btn_add_favorite)
        menuItem?.apply {
            icon = if (state) {
                ContextCompat.getDrawable(this@DetailActivity, R.drawable.ic_favorited)
            } else {
                ContextCompat.getDrawable(this@DetailActivity, R.drawable.ic_favorite)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        this.menu = menu
        val extras = intent?.extras
        if (extras != null) {
            val type = extras.getString(EXTRA_TYPE)
            when {
                type.equals(TYPE_MOVIE, ignoreCase = true) -> {
                    viewModel.movieDetail.observe(this, movieObserver(TYPE_MOVIE))
                }
                type.equals(TYPE_TVSHOW, ignoreCase = true) -> {
                    viewModel.tvShowDetail.observe(this, movieObserver(TYPE_TVSHOW))
                }
            }
        }
        return true
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_TYPE = "extra_type"
        const val TYPE_TVSHOW = "type_tvshow"
        const val TYPE_MOVIE ="type_movie"
    }
}
@file:Suppress("unused", "unused", "unused")

package com.rijaldev.inmovies.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.rijaldev.inmovies.R
import com.rijaldev.inmovies.databinding.ActivityMainBinding
import com.rijaldev.inmovies.ui.dashboard.movie.MovieViewModel
import com.rijaldev.inmovies.ui.dashboard.tvshow.TvShowViewModel
import com.rijaldev.inmovies.ui.favorite.movie.FavoriteMovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModelMovie: MovieViewModel by viewModels()
    private val viewModelTv: TvShowViewModel by viewModels()
    private val viewModelMovieFavorite: FavoriteMovieViewModel by viewModels()
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding?.bottomNav?.setupWithNavController(navController)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
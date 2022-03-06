package com.rijaldev.inmovies.ui.dashboard.movie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rijaldev.inmovies.R
import com.rijaldev.inmovies.data.source.local.entity.MovieEntity
import com.rijaldev.inmovies.databinding.BottomSheetDialogBinding
import com.rijaldev.inmovies.databinding.FragmentMovieBinding
import com.rijaldev.inmovies.utils.SortUtils
import com.rijaldev.inmovies.vo.Resource
import com.rijaldev.inmovies.vo.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment(), MovieFragmentCallback {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding
    private lateinit var movieAdapter: MovieAdapter
    private val viewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            movieAdapter = MovieAdapter(this)
            viewModel.getMovies(SortUtils.DEFAULT).observe(viewLifecycleOwner, observer)

            buttonSortListener()

            binding?.rvMovie?.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }

    private val observer = Observer<Resource<PagedList<MovieEntity>>> { listMovies ->
        if (listMovies != null) {
            when (listMovies.status) {
                Status.LOADING -> {
                    binding?.apply {
                        progressBar.visibility = View.VISIBLE
                        ivNotFound.visibility = View.GONE
                    }
                }
                Status.SUCCESS -> {
                    movieAdapter.submitList(listMovies.data)
                    binding?.apply {
                        progressBar.visibility = View.GONE
                        ivNotFound.visibility = View.GONE
                    }
                }
                Status.ERROR -> {
                    binding?.apply {
                        progressBar.visibility = View.GONE
                        ivNotFound.visibility = View.VISIBLE
                        sortContainer.visibility = View.GONE
                    }
                    Toast.makeText(requireActivity(), "Terjadi Kesalahan!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun buttonSortListener() {
        binding?.apply {
            tvSortAsc.setOnClickListener {
                viewModel.getMovies(SortUtils.ASC)
                    .observe(viewLifecycleOwner, observer)
            }

            tvSortDesc.setOnClickListener {
                viewModel.getMovies(SortUtils.DESC)
                    .observe(viewLifecycleOwner, observer)
            }

            tvSortRating.setOnClickListener {
                viewModel.getMovies(SortUtils.RATING)
                    .observe(viewLifecycleOwner, observer)
            }

            tvSortDefault.setOnClickListener {
                viewModel.getMovies(SortUtils.DEFAULT)
                    .observe(viewLifecycleOwner, observer)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMenuClick(movie: MovieEntity) {
        if (activity != null) {
            val dialog = BottomSheetDialog(requireActivity())
            val view = BottomSheetDialogBinding.inflate(layoutInflater)
            dialog.setContentView(view.root)
            dialog.show()
            view.btnShare.setOnClickListener {
                dialog.dismiss()
                val mimeType = "text/plain"
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        resources.getString(R.string.text_share, movie.title)
                    )
                    type = mimeType
                }
                val shareIntent = Intent.createChooser(sendIntent, "Bagikan aplikasi ini sekarang")
                startActivity(shareIntent)
            }
        }
    }
}
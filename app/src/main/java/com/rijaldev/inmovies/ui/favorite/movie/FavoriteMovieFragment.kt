package com.rijaldev.inmovies.ui.favorite.movie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rijaldev.inmovies.R
import com.rijaldev.inmovies.data.source.local.entity.DetailEntity
import com.rijaldev.inmovies.databinding.BottomSheetDialogBinding
import com.rijaldev.inmovies.databinding.FragmentFavoriteMovieBinding
import com.rijaldev.inmovies.ui.favorite.FavoriteCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteMovieFragment : Fragment(), FavoriteCallback {

    private var _binding: FragmentFavoriteMovieBinding? = null
    private val binding get() = _binding
    private val viewModel: FavoriteMovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteMovieBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val favoriteMovieAdapter = FavoriteMovieAdapter(this)

        viewModel.getFavoriteMovies().observe(viewLifecycleOwner) { listMovie ->
            binding?.shimmer?.visibility = View.GONE
            favoriteMovieAdapter.submitList(listMovie)

            binding?.ivNoMovie?.apply {
                visibility = if (listMovie.isNotEmpty()) View.GONE else View.VISIBLE
            }
        }

        binding?.rvFavMovie?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = favoriteMovieAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onShareClick(detailEntity: DetailEntity) {
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
                    putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.text_share, detailEntity.title))
                    type = mimeType
                }
                val shareIntent = Intent.createChooser(sendIntent, "Bagikan aplikasi ini sekarang")
                startActivity(shareIntent)
            }
        }
    }
}
package com.rijaldev.inmovies.ui.dashboard.tvshow

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
import com.rijaldev.inmovies.data.source.local.entity.TvShowEntity
import com.rijaldev.inmovies.databinding.BottomSheetDialogBinding
import com.rijaldev.inmovies.databinding.FragmentTvShowBinding
import com.rijaldev.inmovies.utils.SortUtils
import com.rijaldev.inmovies.utils.ViewVisibilityUtils.setViewGone
import com.rijaldev.inmovies.utils.ViewVisibilityUtils.setViewVisible
import com.rijaldev.inmovies.vo.Resource
import com.rijaldev.inmovies.vo.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowFragment : Fragment(), TvShowFragmentCallback {

    private var _binding: FragmentTvShowBinding? = null
    private val binding get() = _binding
    private lateinit var tvShowAdapter: TvShowAdapter
    private val viewModel: TvShowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTvShowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            tvShowAdapter = TvShowAdapter(this)

            viewModel.getTvShows(SortUtils.DEFAULT)
                .observe(viewLifecycleOwner, observer)

            binding?.rvTv?.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvShowAdapter
            }
            buttonSortListener()
        }
    }

    private val observer = Observer<Resource<PagedList<TvShowEntity>>> { listTvShows ->
        if (listTvShows != null) {
            when (listTvShows.status) {
                Status.LOADING -> {
                    binding?.apply {
                        setViewVisible(contentShimmer.root)
                        setViewGone(ivNotFound)
                    }
                }
                Status.SUCCESS -> {
                    tvShowAdapter.submitList(listTvShows.data)
                    binding?.apply {
                        setViewGone(contentShimmer.root, scrollContainer, ivNotFound)
                    }
                }
                Status.ERROR -> {
                    binding?.apply {
                        setViewGone(contentShimmer.root, scrollContainer, sortContainer)
                        setViewVisible(ivNotFound)
                    }
                    Toast.makeText(requireActivity(), "Terjadi Kesalahan!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun buttonSortListener() {
        binding?.apply {
            tvSortAsc.setOnClickListener {
                viewModel.getTvShows(SortUtils.ASC)
                    .observe(viewLifecycleOwner, observer)
            }

            tvSortDesc.setOnClickListener {
                viewModel.getTvShows(SortUtils.DESC)
                    .observe(viewLifecycleOwner, observer)
            }

            tvSortRating.setOnClickListener {
                viewModel.getTvShows(SortUtils.RATING)
                    .observe(viewLifecycleOwner, observer)
            }

            tvSortDefault.setOnClickListener {
                viewModel.getTvShows(SortUtils.DEFAULT)
                    .observe(viewLifecycleOwner, observer)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMenuClick(tvShow: TvShowEntity) {
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
                    putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.text_share, tvShow.title))
                    type = mimeType
                }
                val shareIntent = Intent.createChooser(sendIntent, "Bagikan aplikasi ini sekarang")
                startActivity(shareIntent)
            }
        }
    }
}
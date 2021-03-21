package com.example.akiraito.codechallenge.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.akiraito.codechallenge.databinding.FragmentFavoriteBinding
import com.example.akiraito.codechallenge.domain.model.Movie
import com.example.akiraito.codechallenge.utility.DialogUtils
import com.example.akiraito.codechallenge.weget.recycler.GridItemDecoration
import com.example.akiraito.codechallenge.weget.recycler.adapter.MovieGridAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel by viewModel<FavoriteViewModel>()
    private lateinit var movieGridAdapter: MovieGridAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false).also {
            it.vm = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }

        setSubscribeMoveToFragment()
        setSubscribeMovieInfoList()
        setSubscribeNWError()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchFavoriteMovie()
    }

    /**
     * [FavoriteViewModel.shouldDisplayNWError]を監視する
     */
    private fun setSubscribeNWError() {
        val alertDialog = DialogUtils.showErrorDialog(requireContext())
        viewModel.shouldDisplayNWError.observe(viewLifecycleOwner, Observer {
            // NWエラーダイアログが既に表示されている場合は表示しない
            if (!alertDialog.isShowing) {
                alertDialog.show()
            }
        })
    }

    /**
     * [FavoriteViewModel.movieInfoList]を監視する
     */
    private fun setSubscribeMovieInfoList() {
        viewModel.movieInfoList.observe(viewLifecycleOwner, Observer {
            movieGridAdapter = MovieGridAdapter(viewModel::onMovieClick, it as ArrayList)
            binding.recyclerFavoriteMovie.also { view ->
                view.adapter = movieGridAdapter
                view.addItemDecoration(GridItemDecoration(5, requireContext()))
            }
        })
    }


    /**
     * [FavoriteViewModel.moveToFragment]をsubscribe
     */
    private fun setSubscribeMoveToFragment() {
        viewModel.moveToFragment.observe(viewLifecycleOwner, Observer {
            when (it.first) {
                FavoriteViewModel.MoveToFragmentType.DETAIL -> {
                    (it.second as Movie).let { movie ->
                        val action =
                            FavoriteFragmentDirections.actionNavigationFavoriteToNavigationMovieDetail(
                                movie.title, movie.id
                            )
                        findNavController().navigate(action)
                    }
                }
                FavoriteViewModel.MoveToFragmentType.SEARCH -> {
                    val action =
                        FavoriteFragmentDirections.actionNavigationFavoriteToNavigationSearch()
                    findNavController().navigate(action)
                }
            }
        })
    }
}
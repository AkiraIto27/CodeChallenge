package com.example.akiraito.codechallenge.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.example.akiraito.codechallenge.databinding.FragmentHomeBinding
import com.example.akiraito.codechallenge.domain.model.Movie
import com.example.akiraito.codechallenge.utility.DialogUtils
import com.example.akiraito.codechallenge.weget.recycler.adapter.HorizontalScrollMovieListAdapter
import com.example.akiraito.codechallenge.weget.recycler.adapter.RecommendMovieRecyclerAdapter
import com.example.akiraito.codechallenge.weget.recycler.adapter.SlideImageAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var recommendMovieRecyclerAdapter: RecommendMovieRecyclerAdapter
    private lateinit var slideImageAdapter: SlideImageAdapter
    private lateinit var horizontalMovieAdapter: HorizontalScrollMovieListAdapter
    private val viewModel: HomeViewModel by viewModel()
    private var adapter = ConcatAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false).also {
            it.vm = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }

        setupRecyclerView()
        setSubscribeRecommendMovieList()
        setSubscribeNWError()
        setSubscribeGenresMovieList()
        setSubscribeMoveToFragment()
        setSubscribeShouldClearGenresMovie()
        viewModel.fetchGenres()
        viewModel.fetchRecommendMovie()

        return binding.root
    }

    /**
     * 映画一覧のView設定
     */
    private fun setupRecyclerView() {
        // 映画一覧と読み込みフッターのアダプタをセット
        slideImageAdapter = SlideImageAdapter(
            onItemClickListener = viewModel::findMovie
        )
        recommendMovieRecyclerAdapter = RecommendMovieRecyclerAdapter(
            onItemClickListener = viewModel::onItemClick
        )
        adapter = ConcatAdapter(slideImageAdapter, recommendMovieRecyclerAdapter)

        binding.recyclerAllMovieList.also {
            it.adapter = adapter
        }
    }

    /**
     * [HomeViewModel.shouldDisplayNWError]を監視する
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
     * [HomeViewModel.slidedMovieList]をsubscribe
     */
    private fun setSubscribeRecommendMovieList() {
        viewModel.slidedMovieList.observe(viewLifecycleOwner, Observer { movieList ->
            slideImageAdapter.updateSlideImage(movieList)
        })
    }

    /**
     * [HomeViewModel.shouldClearGenresMovie]をsubscribe
     */
    private fun setSubscribeShouldClearGenresMovie() {
        viewModel.shouldClearGenresMovie.observe(viewLifecycleOwner, Observer {
            viewModel.adapterList.forEach { removeAdapter ->
                adapter.removeAdapter(removeAdapter)
            }
            viewModel.adapterList.clear()
        })
    }

    /**
     * [HomeViewModel.genresMovieList]をsubscribe
     */
    private fun setSubscribeGenresMovieList() {
        viewModel.genresMovieList.observe(viewLifecycleOwner, Observer { movieList ->
            movieList?.let {
                horizontalMovieAdapter =
                    HorizontalScrollMovieListAdapter(movieList.second, viewModel::onItemClick)
                horizontalMovieAdapter.updateMovieList(movieList.first)
                adapter.addAdapter(horizontalMovieAdapter)
                viewModel.setAdapterList(horizontalMovieAdapter)
            }
        })
    }

    /**
     * [HomeViewModel.moveToFragment]をsubscribe
     */
    private fun setSubscribeMoveToFragment() {
        viewModel.moveToFragment.observe(viewLifecycleOwner, Observer {
            when (it.first) {
                HomeViewModel.MoveToFragmentType.DETAIL -> {
                    (it.second as Movie).let { movie ->
                        val action =
                            HomeFragmentDirections.actionNavigationHomeToNavigationMovieDetail(
                                movie.title, movie.id
                            )
                        findNavController().navigate(action)
                    }
                }
            }
        })
    }
}
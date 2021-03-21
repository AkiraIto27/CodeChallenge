package com.example.akiraito.codechallenge.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.akiraito.codechallenge.databinding.FragmentSearchBinding
import com.example.akiraito.codechallenge.domain.model.Movie
import com.example.akiraito.codechallenge.extention.textChanges
import com.example.akiraito.codechallenge.utility.DialogUtils
import com.example.akiraito.codechallenge.weget.recycler.GridItemDecoration
import com.example.akiraito.codechallenge.weget.recycler.adapter.MovieGridAdapter
import com.example.akiraito.codechallenge.weget.recycler.adapter.RecommendMovieAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel by viewModel<SearchViewModel>()
    private lateinit var searchResultAdapter: MovieGridAdapter
    private lateinit var recommendMovieAdapter: RecommendMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false).also {
            it.vm = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }

        setSearchBox()
        setSubscribeNWError()
        setSubscribeMovieInfoList()
        setSubscribeMoveToFragment()
        setSubscribeRecommendMovieInfoList()
        setSearchResultRecyclerView()
        viewModel.fetchRecommendMovie()

        return binding.root
    }

    /**
     * Grid形式の検索結果RecyclerViewのセット
     */
    private fun setSearchResultRecyclerView() {
        searchResultAdapter = MovieGridAdapter(viewModel::onMovieClick, arrayListOf())
        binding.recyclerSearchResult.also { view ->
            view.adapter = searchResultAdapter
            view.addItemDecoration(GridItemDecoration(5, requireContext()))
        }
    }

    /**
     * 検索BOXの設置
     */
    private fun setSearchBox() {
        // 検索回数を減らすため文字最終入力0.5秒後に検索開始するためのタイマー
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            binding.textFw
                .textChanges()
                .debounce(TEXT_INPUT_DEBOUNCE_INTERVAL_IN_MILLI_SEC)
                .onEach {
                    if (it.isNullOrEmpty()) {
                        viewModel.showRecommendMovie()
                    } else {
                        viewModel.searchMovie(it)
                    }
                }.launchIn(lifecycleScope)
        }
    }

    /**
     * [SearchViewModel.movieInfoList]を監視する
     */
    private fun setSubscribeMovieInfoList() {
        viewModel.movieInfoList.observe(viewLifecycleOwner, Observer {
            searchResultAdapter.updateMovieList(it)
        })
    }

    /**
     * [SearchViewModel.recommendMovieInfoList]を監視する
     */
    private fun setSubscribeRecommendMovieInfoList() {
        viewModel.recommendMovieInfoList.observe(viewLifecycleOwner, Observer {
            recommendMovieAdapter = RecommendMovieAdapter(viewModel::onMovieClick, it)
            binding.recyclerRecommendMovieList.adapter = recommendMovieAdapter
        })
    }

    /**
     * [SearchViewModel.shouldDisplayNWError]を監視する
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
     * [SearchViewModel.moveToFragment]をsubscribe
     */
    private fun setSubscribeMoveToFragment() {
        viewModel.moveToFragment.observe(viewLifecycleOwner, Observer {
            when (it.first) {
                SearchViewModel.MoveToFragmentType.DETAIL -> {
                    (it.second as Movie).let { movie ->
                        val action =
                            SearchFragmentDirections.actionNavigationSearchToNavigationMovieDetail(
                                movie.title, movie.id
                            )
                        findNavController().navigate(action)
                    }
                }
            }
        })
    }

    companion object {
        private const val TEXT_INPUT_DEBOUNCE_INTERVAL_IN_MILLI_SEC = 500L
    }
}
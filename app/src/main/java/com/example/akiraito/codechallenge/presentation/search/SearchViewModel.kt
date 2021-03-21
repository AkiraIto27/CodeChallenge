package com.example.akiraito.codechallenge.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.akiraito.codechallenge.ResultStatus
import com.example.akiraito.codechallenge.domain.model.Movie
import com.example.akiraito.codechallenge.domain.repository.MovieRepository
import com.example.akiraito.codechallenge.domain.repository.SearchRepository
import com.example.akiraito.codechallenge.extention.SingleLiveData
import kotlinx.coroutines.launch

class SearchViewModel(
    private val movieRepository: MovieRepository,
    private val searchRepository: SearchRepository
) : ViewModel() {
    /** 映画リスト **/
    private val recommendMovieList = arrayListOf<Movie>()

    /** 検索したい文字列 **/
    val fwText = MutableLiveData("")

    /** エラーダイアログ表示通知 **/
    private val _shouldDisplayNWError = SingleLiveData<Unit>()
    val shouldDisplayNWError: LiveData<Unit> = _shouldDisplayNWError

    /** 映画情報リスト **/
    private val _movieInfoList = SingleLiveData<List<Movie>>()
    val movieInfoList: LiveData<List<Movie>> = _movieInfoList

    /** オススメ映画情報リスト **/
    private val _recommendMovieInfoList = SingleLiveData<List<Movie>>()
    val recommendMovieInfoList: LiveData<List<Movie>> = _recommendMovieInfoList

    /** Fragment移動通知 **/
    private val _moveToFragment = SingleLiveData<Pair<MoveToFragmentType, Any>>()
    val moveToFragment: LiveData<Pair<MoveToFragmentType, Any>> = _moveToFragment

    /** emptyView表示通知 **/
    private val _shouldDisplayEmptyView = SingleLiveData<Boolean>()
    val shouldDisplayEmptyView: LiveData<Boolean> = _shouldDisplayEmptyView

    /**
     * オススメ映画の取得
     */
    fun fetchRecommendMovie() {
        viewModelScope.launch {
            when (val result = movieRepository.fetchMovieList(1)) {
                is ResultStatus.Success -> {
                    _shouldDisplayEmptyView.postValue(false)
                    recommendMovieList.addAll(result.values.movieList)
                    _recommendMovieInfoList.postValue(result.values.movieList)
                }
                is ResultStatus.Error -> {
                    _shouldDisplayNWError.postValue(Unit)
                    _shouldDisplayEmptyView.postValue(true)
                }
            }
        }
    }

    /**
     * オススメ映画を表示
     */
    fun showRecommendMovie() {
        if (recommendMovieList.isEmpty()) {
            fetchRecommendMovie()
        } else {
            _shouldDisplayEmptyView.postValue(false)
            _recommendMovieInfoList.postValue(recommendMovieList)
        }
    }

    /**
     * フリーワード検索
     */
    fun searchMovie(freeWordText: String) {
        _movieInfoList.postValue(listOf())
        viewModelScope.launch {
            when (val result = searchRepository.searchFwMovie(freeWordText)) {
                is ResultStatus.Success -> {
                    _shouldDisplayEmptyView.postValue(false)
                    val movieList = result.values
                    _movieInfoList.postValue(movieList)
                }
                is ResultStatus.Error -> {
                    _shouldDisplayNWError.postValue(Unit)
                    _shouldDisplayEmptyView.postValue(true)
                }
            }
        }
    }

    /**
     * textクリアボタン押下時の処理
     */
    fun onTextClear() {
        fwText.postValue("")
    }

    /**
     * 映画セルをクリックしたときの処理
     */
    fun onMovieClick(movie: Movie) {
        _moveToFragment.postValue(MoveToFragmentType.DETAIL to movie)
    }

    /**
     * 遷移先タイプ
     * 今後の拡張性を考慮してenumで作成
     */
    enum class MoveToFragmentType {
        DETAIL
    }
}
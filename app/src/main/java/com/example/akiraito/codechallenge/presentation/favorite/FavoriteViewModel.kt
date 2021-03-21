package com.example.akiraito.codechallenge.presentation.favorite

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.akiraito.codechallenge.ResultStatus
import com.example.akiraito.codechallenge.domain.model.Movie
import com.example.akiraito.codechallenge.domain.repository.MovieRepository
import com.example.akiraito.codechallenge.extention.SingleLiveData
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val movieRepository: MovieRepository,
    private val favoritePreferences: SharedPreferences
) : ViewModel() {
    /** 映画情報リスト **/
    private val _movieInfoList = SingleLiveData<List<Movie>>()
    val movieInfoList: LiveData<List<Movie>> = _movieInfoList

    /** エラーダイアログ表示通知 **/
    private val _shouldDisplayNWError = SingleLiveData<Unit>()
    val shouldDisplayNWError: LiveData<Unit> = _shouldDisplayNWError

    /** emptyView表示通知 **/
    private val _shouldDisplayEmptyView = SingleLiveData<Boolean>()
    val shouldDisplayEmptyView: LiveData<Boolean> = _shouldDisplayEmptyView

    /** Fragment移動通知 **/
    private val _moveToFragment = SingleLiveData<Pair<MoveToFragmentType, Any>>()
    val moveToFragment: LiveData<Pair<MoveToFragmentType, Any>> = _moveToFragment

    /** 映画リスト **/
    private val movieList = arrayListOf<Movie>()

    /**
     * お気に入り映画一覧を取得
     */
    fun fetchFavoriteMovie() {
        movieList.clear()
        val idList = favoritePreferences.all.values
        if (idList.isEmpty()) {
            // emptyViewを表示する
            _shouldDisplayEmptyView.postValue(true)
        } else {
            // 映画一覧を取得する
            // Apiの仕様上一括検索ができないため１つ１つfetchしていく
            viewModelScope.launch {
                idList.map { fetchMovieInfo(it as Int) }
                _movieInfoList.postValue(movieList)
            }
        }
    }

    /**
     * movieIdを元に映画情報を取得する
     */
    private suspend fun fetchMovieInfo(movieId: Int) {
        when (val result = movieRepository.fetchMovieDetail(movieId)) {
            is ResultStatus.Success -> {
                val movie = result.values
                movieList.add(movie)
                _shouldDisplayEmptyView.postValue(false)
            }
            is ResultStatus.Error -> {
                _shouldDisplayNWError.postValue(Unit)
                _shouldDisplayEmptyView.postValue(true)
            }
        }
    }

    /**
     * 映画セルをクリックしたときの処理
     */
    fun onMovieClick(movie: Movie) {
        _moveToFragment.postValue(MoveToFragmentType.DETAIL to movie)
    }

    /**
     * 探しに行くボタンをクリックしたときの処理
     */
    fun onSearchButtonClick() {
        _moveToFragment.postValue(MoveToFragmentType.SEARCH to Unit)
    }

    /**
     * 遷移先タイプ
     * 今後の拡張性を考慮してenumで作成
     */
    enum class MoveToFragmentType {
       DETAIL, SEARCH
    }
}
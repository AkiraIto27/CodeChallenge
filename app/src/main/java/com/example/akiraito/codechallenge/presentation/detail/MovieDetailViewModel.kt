package com.example.akiraito.codechallenge.presentation.detail

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.akiraito.codechallenge.BuildConfig
import com.example.akiraito.codechallenge.ResultStatus
import com.example.akiraito.codechallenge.domain.model.Movie
import com.example.akiraito.codechallenge.domain.repository.MovieRepository
import com.example.akiraito.codechallenge.extention.SingleLiveData
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movieRepository: MovieRepository,
    private val favoritePreferences: SharedPreferences
) : ViewModel() {
    /** 映画情報 **/
    private val _movieInfo = MutableLiveData<Movie>()
    val movieInfo: LiveData<Movie> = _movieInfo

    /** いいねボタン設定準備完了通知 **/
    private val _likeButton = SingleLiveData<Unit>()
    val likeButton: LiveData<Unit> = _likeButton

    /** 映画情報 **/
    private val _subtitle = MutableLiveData<String>()
    val subtitle: LiveData<String> = _subtitle

    /** 映画情報 **/
    private val _genres = MutableLiveData<String>()
    val genres: LiveData<String> = _genres

    /** 映画情報 **/
    private val _shouldShowShareView = SingleLiveData<String>()
    val shouldShowShareView: LiveData<String> = _shouldShowShareView

    /** エラーダイアログ表示通知 **/
    private val _shouldDisplayNWError = SingleLiveData<Unit>()
    val shouldDisplayNWError: LiveData<Unit> = _shouldDisplayNWError

    /**
     * 映画の詳細を取得
     */
    fun fetchMovieDetail(movieId: Int) {
        viewModelScope.launch {
            when (val result = movieRepository.fetchMovieDetail(movieId)) {
                is ResultStatus.Success -> {
                    val movie = result.values
                    _movieInfo.postValue(movie)
                    _likeButton.postValue(Unit)
                    setSubtitle(movie)
                    setGenres(movie)
                }
                is ResultStatus.Error -> {
                    _shouldDisplayNWError.postValue(Unit)
                }
            }
        }
    }

    /**
     * 字幕のセット
     */
    private fun setSubtitle(movie: Movie) {
        val subtitleList = movie.spokenLanguages?.map { it.name }
        val subtitle = subtitleList?.joinToString(separator = "・")
        _subtitle.postValue(subtitle)
    }

    /**
     * ジャンルをセット
     */
    private fun setGenres(movie: Movie) {
        val genresList = movie.genres?.map { it.name }
        val genres = genresList?.joinToString(separator = "・")
        _genres.postValue(genres)
    }

    /**
     * 「共有」を表示
     */
    fun showSnsShare() {
        val url = createLinkUrl() ?: return
        _shouldShowShareView.postValue(url)
    }

    /**
     * SNSシェアのリンクの文字列を生成
     *
     * @return リンクの文字列
     */
    private fun createLinkUrl(): String? {
        return BuildConfig.SHARE_URL + _movieInfo.value?.id?.toString()
    }

    /**
     * preferenceに映画IDを保存
     */
    fun setFavoritePreferences() {
        _movieInfo.value?.let {
            favoritePreferences.edit().putInt(it.title, it.id).apply()
        }
    }

    /**
     * preferenceから映画IDを削除
     */
    fun removeFavoritePreferences() {
        _movieInfo.value?.let {
            favoritePreferences.edit().remove(it.title).apply()
        }
    }

    /**
     * preferenceに映画IDが入っているか確認
     */
    fun isCheckFavoriteMovieIdPreferences(): Boolean {
        _movieInfo.value?.also {
            return favoritePreferences.getInt(it.title, 0) == 0
        }
        return false
    }

}
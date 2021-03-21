package com.example.akiraito.codechallenge.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denzcoskun.imageslider.models.SlideModel
import com.example.akiraito.codechallenge.BuildConfig
import com.example.akiraito.codechallenge.ResultStatus
import com.example.akiraito.codechallenge.domain.model.Genres
import com.example.akiraito.codechallenge.domain.model.Movie
import com.example.akiraito.codechallenge.domain.repository.DiscoverRepository
import com.example.akiraito.codechallenge.domain.repository.GenresRepository
import com.example.akiraito.codechallenge.domain.repository.MovieRepository
import com.example.akiraito.codechallenge.extention.SingleLiveData
import com.example.akiraito.codechallenge.weget.recycler.adapter.HorizontalScrollMovieListAdapter
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class HomeViewModel(
    private val movieRepository: MovieRepository,
    private val discoverRepository: DiscoverRepository,
    private val genresRepository: GenresRepository
) : ViewModel() {
    /** adapterリスト **/
    val adapterList = arrayListOf<HorizontalScrollMovieListAdapter>()

    /** 映画リスト **/
    private val recommendMovieList = arrayListOf<Movie>()

    /** 追加読み込み用 **/
    private var page: Int = 1

    /** オススメ映画の取得通知 **/
    private val _slidedMovieList = SingleLiveData<ArrayList<SlideModel>>()
    val slidedMovieList: LiveData<ArrayList<SlideModel>> = _slidedMovieList

    /** ジャンル毎の映画を取得した通知 **/
    private val _genresMovieList = SingleLiveData<Pair<List<Movie>, Genres>?>()
    val genresMovieList: LiveData<Pair<List<Movie>, Genres>?> = _genresMovieList

    /** エラーダイアログ表示通知 **/
    private val _shouldDisplayNWError = SingleLiveData<Unit>()
    val shouldDisplayNWError: LiveData<Unit> = _shouldDisplayNWError

    /** Fragment移動通知 **/
    private val _moveToFragment = SingleLiveData<Pair<MoveToFragmentType, Any>>()
    val moveToFragment: LiveData<Pair<MoveToFragmentType, Any>> = _moveToFragment

    /** SwipeRefreshLayoutのindicator表示フラグ **/
    private val _shouldDisplayRefreshIndicator = SingleLiveData<Boolean>()
    val shouldDisplayRefreshIndicator: LiveData<Boolean> = _shouldDisplayRefreshIndicator

    /** ジャンル毎の映画一覧をクリアする **/
    private val _shouldClearGenresMovie = SingleLiveData<Unit>()
    val shouldClearGenresMovie: LiveData<Unit> = _shouldClearGenresMovie

    /**
     * ジャンルリストを取得する
     */
    fun fetchGenres() {
        viewModelScope.launch {
            when (val result = genresRepository.fetchGenres()) {
                is ResultStatus.Success -> {
                    val genreList = result.values
                    genreList.map { fetchDiscoverMovieWithGenres(it) }
                }
                is ResultStatus.Error -> {
                    _shouldDisplayNWError.postValue(Unit)
                }
            }
        }
    }

    /**
     * ジャンル指定した映画の取得
     */
    private suspend fun fetchDiscoverMovieWithGenres(genre: Genres) {
        when (val result = discoverRepository.fetchMovie(genre)) {
            is ResultStatus.Success -> {
                val movieList = result.values
                _genresMovieList.setValue(null)
                _genresMovieList.postValue(movieList to genre)
            }
            is ResultStatus.Error -> {
                _shouldDisplayNWError.postValue(Unit)
            }
        }
    }

    /**
     * オススメ映画の取得
     */
    fun fetchRecommendMovie() {
        viewModelScope.launch {
            when (val result = movieRepository.fetchMovieList(page)) {
                is ResultStatus.Success -> {
                    page++
                    val imageList = ArrayList<SlideModel>()
                    recommendMovieList.addAll(result.values.movieList)
                    recommendMovieList.map { movie ->
                        imageList.add(
                            SlideModel(
                                BuildConfig.IMAGE_BACE_URL + movie.backdropPath,
                                movie.title
                            )
                        )
                    }
                    _slidedMovieList.postValue(imageList)
                }
                is ResultStatus.Error -> {
                    _shouldDisplayNWError.postValue(Unit)
                }
            }
        }
    }

    /**
     * 映画タイトルを元に映画を情報を取得
     */
    fun findMovie(title: String?) {
        val movie = recommendMovieList.first {
            it.title == title
        }
        _moveToFragment.postValue(MoveToFragmentType.DETAIL to movie)
    }

    /**
     * HorizontalScrollMovieListAdapterを１つずつConcatAdapterに詰め込んでるため
     * Refresh用にadapterを保存しておく
     */
    fun setAdapterList(adapter: HorizontalScrollMovieListAdapter) {
        adapterList.add(adapter)
    }

    /**
     * データのリフレッシュ
     */
    fun onRefresh() {
        _shouldDisplayRefreshIndicator.postValue(true)
        _shouldClearGenresMovie.postValue(Unit)
        viewModelScope.launch {
            val deferredList =
                listOf(async { fetchGenres() }, async { fetchRecommendMovie() })
            deferredList.awaitAll()
            _shouldDisplayRefreshIndicator.postValue(false)
        }
    }

    /**
     * 映画セルをクリックした時の挙動
     */
    fun onItemClick(movie: Movie) {
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
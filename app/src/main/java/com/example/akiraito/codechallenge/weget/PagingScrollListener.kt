package com.example.akiraito.codechallenge.weget

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * ページングのlistener
 */
abstract class PagingScrollListener(
    private var layoutManager: GridLayoutManager,
    private var visibleThreshold: Int = 0
) : RecyclerView.OnScrollListener() {
    private var firstVisibleItem = 0
    private var previousTotalItem = 0
    private var isLoading = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        // 上にスクロールした時は検知しない
        if (dy <= 0) return

        firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

        if (shouldStartMoreLoad(recyclerView, firstVisibleItem)) {
            isLoading = true
            onLoadMore()
        }
    }

    private fun shouldStartMoreLoad(
        recyclerView: RecyclerView,
        firstVisibleItemPosition: Int
    ): Boolean {
        val totalItemCount = layoutManager.itemCount

        if (isLoading) {
            // 前回との差がある場合のみ
            if (totalItemCount > previousTotalItem + visibleThreshold || shouldLoadMoreRetry) {
                isLoading = false
                previousTotalItem = totalItemCount
            }
        }

        val visibleItemCount = recyclerView.childCount
        if (visibleItemCount <= 0 || isLoading) {
            return false
        }

        return totalItemCount <= firstVisibleItemPosition + visibleThreshold
    }

    /**
     * 保持内容をリセット
     */
    fun reset() {
        firstVisibleItem = 0
        previousTotalItem = 0
        isLoading = true
    }

    abstract fun onLoadMore()

    /**
     * 再度、追加読み込みを行うかどうかのフラグ
     * 追加読み込み失敗時にtrueにする
     */
    abstract var shouldLoadMoreRetry: Boolean
}

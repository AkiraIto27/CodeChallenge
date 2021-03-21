package com.example.akiraito.codechallenge.weget.recycler

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridItemDecoration(private val space: Int, val context: Context) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = convertPxToDp(space, context)
        outRect.left = convertPxToDp(space, context)
        outRect.right = convertPxToDp(space, context)
        outRect.bottom = convertPxToDp(space, context)
    }

    /**
     * dp指定したい数値をIntへ変換
     * @param dp 指定したい数値
     * @param context
     * @return float dp
     */
    private fun convertPxToDp(dp: Int, context: Context): Int {
        val metrics = context.resources.displayMetrics
        return (dp * metrics.density).toInt()
    }
}
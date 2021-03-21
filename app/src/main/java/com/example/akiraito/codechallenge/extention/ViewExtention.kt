package com.example.akiraito.codechallenge.extention

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.akiraito.codechallenge.weget.SingleClickListener

@BindingAdapter("onSingleClick")
fun View.setOnSingleClickListener(onClick: () -> Unit) =
    setOnClickListener(SingleClickListener {
        onClick()
    })

@BindingAdapter("showRefreshIndicator")
fun SwipeRefreshLayout.showRefreshIndicator(isRefresh: Boolean) {
    this.isRefreshing = isRefresh
}
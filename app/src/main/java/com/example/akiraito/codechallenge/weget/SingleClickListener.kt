package com.example.akiraito.codechallenge.weget

import android.view.View

/**
 * Viewのダブルクリックによる誤操作を防止
 *
 * @param click クリックイベント
 */
class SingleClickListener(private val click: (v: View) -> Unit) : View.OnClickListener {

    private var lastClickTime: Long = 0

    override fun onClick(v: View) {
        if (getLastClickTimeout() > DOUBLE_CLICK_TIMEOUT) {
            lastClickTime = System.currentTimeMillis()
            click(v)
        }
    }

    private fun getLastClickTimeout(): Long {
        return System.currentTimeMillis() - lastClickTime
    }

    companion object {
        private const val DOUBLE_CLICK_TIMEOUT = 1000L
    }
}
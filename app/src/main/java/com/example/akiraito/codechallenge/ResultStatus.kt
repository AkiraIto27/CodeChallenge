package com.example.akiraito.codechallenge

/**
 * 通信結果のステータス
 */
sealed class ResultStatus<out T> {
    /** 正常終了 */
    data class Success<out T>(val values: T) : ResultStatus<T>()

    /** 異常終了 */
    data class Error(val exception: Throwable) : ResultStatus<Nothing>()
}
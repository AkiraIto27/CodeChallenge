package com.example.akiraito.codechallenge.utility

import android.app.AlertDialog
import android.content.Context
import com.example.akiraito.codechallenge.R

/**
 * ダイアログに関するユーティリティ
 */
object DialogUtils {
    /**
     * textが入ってる部分のみ表示される
     * 最も単純な形のAlertDialogを表示します。
     *
     * @param title タイトル部分に表示するテキスト
     * @param message メッセージ部分に表示するテキスト
     * @param positiveText ポシティブボタンに表示するテキスト
     * @param negativeText ネガティブボタンに表示するテキスト
     * @param positiveButtonClick ポシティブボタン押下時の処理
     * @param negativeButtonClick ポシティブボタン押下時の処理
     */
    @JvmStatic
    fun showNormalAlertDialog(
        context: Context,
        title: String?,
        message: String? = null,
        positiveText: String? = null,
        negativeText: String? = null,
        neutralText: String? = null,
        positiveButtonClick: (() -> Unit)? = null,
        negativeButtonClick: (() -> Unit)? = null,
        NeutralButtonClick: (() -> Unit)? = null
    ) {
        AlertDialog.Builder(context).also {
            if (!title.isNullOrEmpty()) {
                it.setTitle(title)
            }
            if (!message.isNullOrEmpty()) {
                it.setMessage(message)
            }
            if (!positiveText.isNullOrEmpty()) {
                it.setPositiveButton(positiveText) { _, _ ->
                    if (positiveButtonClick != null) {
                        positiveButtonClick()
                    }
                }
            }
            if (!negativeText.isNullOrEmpty()) {
                it.setNegativeButton(negativeText) { _, _ ->
                    if (negativeButtonClick != null) {
                        negativeButtonClick()
                    }
                }
            }
            if (!neutralText.isNullOrEmpty()) {
                it.setNeutralButton(neutralText) { _, _ ->
                    if (NeutralButtonClick != null) {
                        NeutralButtonClick()
                    }
                }
            }
            it.show().let { dialog ->
                if (!positiveText.isNullOrEmpty()) {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).let { positiveButton ->
                        positiveButton.setTextColor(context.getColor(R.color.dialog_button_text))
                        positiveButton.setBackgroundColor(context.getColor(R.color.dialog_button))
                    }
                }
                if (!negativeText.isNullOrEmpty()) {
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).let { negativeButton ->
                        negativeButton.setTextColor(context.getColor(R.color.dialog_button_text))
                        negativeButton.setBackgroundColor(context.getColor(R.color.dialog_button))
                    }
                }
                if (!negativeText.isNullOrEmpty()) {
                    dialog.getButton(AlertDialog.BUTTON_NEUTRAL).let { negativeButton ->
                        negativeButton.setTextColor(context.getColor(R.color.dialog_button_text))
                        negativeButton.setBackgroundColor(context.getColor(R.color.dialog_button))
                    }
                }
            }
        }
    }

    /**
     * textが入ってる部分のみ表示される
     * 背景がタップできないAlertDialogを表示します。
     *
     * @param title タイトル部分に表示するテキスト
     * @param message メッセージ部分に表示するテキスト
     * @param positiveText ポシティブボタンに表示するテキスト
     * @param negativeText ネガティブボタンに表示するテキスト
     * @param positiveButtonClick ポシティブボタン押下時の処理
     * @param negativeButtonClick ポシティブボタン押下時の処理
     */
    @JvmStatic
    fun showAlertDialogBgNoReaction(
        context: Context,
        title: String?,
        message: String?,
        positiveText: String?,
        negativeText: String?,
        positiveButtonClick: (() -> Unit)? = null,
        negativeButtonClick: (() -> Unit)? = null
    ): AlertDialog {
        return AlertDialog.Builder(context).let {
            if (!title.isNullOrEmpty()) {
                it.setTitle(title)
            }
            if (!message.isNullOrEmpty()) {
                it.setMessage(message)
            }
            if (!positiveText.isNullOrEmpty()) {
                it.setPositiveButton(positiveText) { _, _ ->
                    if (positiveButtonClick != null) {
                        positiveButtonClick()
                    }
                }
            }
            if (!negativeText.isNullOrEmpty()) {
                it.setNegativeButton(negativeText) { _, _ ->
                    if (negativeButtonClick != null) {
                        negativeButtonClick()
                    }
                }
            }
            it.setCancelable(false)
            it.create()
        }
    }

    /**
     * ノーマルなエラーダイアログを表示します。
     */
    @JvmStatic
    fun showErrorDialog(
        context: Context,
        positiveText: String? = null,
        positiveButtonClick: (() -> Unit)? = null
    ): AlertDialog {

        return showAlertDialogBgNoReaction(
            context = context,
            title = context.getString(R.string.error_dialog_connection_error_title),
            message = context.getString(R.string.error_dialog_connection_error_text),
            positiveText = positiveText ?: context.getString(R.string.ok),
            negativeText = null,
            positiveButtonClick = positiveButtonClick
        )
    }
}

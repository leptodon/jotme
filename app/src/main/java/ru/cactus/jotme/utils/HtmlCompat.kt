package ru.cactus.jotme.utils

import android.text.Html

import android.os.Build

import android.text.SpannableString

import android.text.Spanned

object HtmlCompat {
    fun fromHtml(html: String?): Spanned {
        return when {
            html == null -> {
                SpannableString("")
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
            }
            else -> {
                Html.fromHtml(html)
            }
        }
    }

    fun toHtml(text: Spanned?): String {
        return when {
            text == null -> {
                ""
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                Html.toHtml(text, Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE)
            }
            else -> {
                Html.toHtml(text)
            }
        }
    }
}

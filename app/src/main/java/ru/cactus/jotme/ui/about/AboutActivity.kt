package ru.cactus.jotme.ui.about

import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import ru.cactus.jotme.R
import ru.cactus.jotme.databinding.AboutActivityBinding

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: AboutActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.about_activity)

        with(binding) {
            ibBoldText.setOnClickListener { setBoldText() }
            ibItalicText.setOnClickListener { setItalicText() }
            ibTextFormat.setOnClickListener { setFormatText() }
            ibPreview.setOnClickListener {
                val rawText = binding.custom.text.toString()
                val rextWithReplace = rawText
                    .replace("&lt;", "<")
                    .replace("&gt;", ">")
                val textToHtml = Html.toHtml(
                    SpannableString.valueOf(rawText)
                )
                val textToHtml2 = Html.toHtml(
                    SpannableString.valueOf(rextWithReplace)
                )

                Log.d("TAG-rawText", rawText)
                Log.d("TAG-textWithReplace", rextWithReplace)
                Log.d("TAG-textToHtml", textToHtml)
                Log.d("TAG-textToHtml2", textToHtml2)
            }
        }

    }

    private fun setBoldText() {
        val start = binding.custom.selectionStart
        val end = binding.custom.selectionEnd
        val text = binding.custom.text.subSequence(start, end).toString()
        val newText = "<b>${text}</b>"

        val rawText = binding.custom.text.toString()

        val textToHtml = Html.escapeHtml(
            rawText
                .replace("&lt;", "<")
                .replace("&gt;", ">")
        )

        Log.d("TAG", textToHtml)
//        binding.custom.text.replace(start, end, newText)
        binding.custom.text = Html.fromHtml(
            binding.custom.text.toString().replace(text, newText).replace("\n", "<br>")
        ) as Editable
    }

    private fun setItalicText() {
        val start = binding.custom.selectionStart
        val end = binding.custom.selectionEnd
        val text = binding.custom.text.subSequence(start, end).toString()
        val newText = "<i>${text}</i>"

        binding.custom.text.replace(start, end, newText)
//
//        binding.custom.text =
//            Html.fromHtml(
//                binding.custom.text.toString().replace(text, newText).replace("\n", "<br>")
//            ) as Editable
    }

    private fun setFormatText() {
        val start = binding.custom.selectionStart
        val end = binding.custom.selectionEnd
        val text = binding.custom.text.subSequence(start, end).toString()
        val newText = "<u>${text}</u>"
        binding.custom.text.replace(start, end, newText)
//
//        binding.custom.text =
//            Html.fromHtml(
//                binding.custom.text.toString().replace(text, newText).replace("\n", "<br>")
//            ) as Editable
    }

}
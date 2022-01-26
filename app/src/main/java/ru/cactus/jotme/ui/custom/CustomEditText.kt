package ru.cactus.jotme.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.Html
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import ru.cactus.jotme.R

@SuppressLint("ViewConstructor")
class CustomEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.editTextStyle,
) : TextInputEditText(context, attrs, defStyleAttr) {

    var htmlText: String? = null
        set(value) {
            field = value
            if (!text.isNullOrEmpty()) text = Html.fromHtml(value) as Editable
        }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomEditText,
            defStyleAttr,
            0
        ).also { typedArray ->
            htmlText = typedArray.getString(R.styleable.CustomEditText_htmlText)
        }.recycle()
    }

}
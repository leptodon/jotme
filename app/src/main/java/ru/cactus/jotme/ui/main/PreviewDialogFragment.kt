package ru.cactus.jotme.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ru.cactus.jotme.R
import ru.cactus.jotme.databinding.CardInfoLayoutBinding
import ru.cactus.jotme.repository.entity.Note

class PreviewDialogFragment(private val note: Note): DialogFragment() {

    private var binding: CardInfoLayoutBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.setCanceledOnTouchOutside(true)
        binding = CardInfoLayoutBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun getTheme(): Int {
        return R.style.DialogTheme
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            tvCardInfoTitle.text = note.title
            tvCardInfoBody.text = note.body
        }
    }

}
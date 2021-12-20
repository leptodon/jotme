package ru.cactus.jotme.ui.preview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.cactus.jotme.R
import ru.cactus.jotme.databinding.FragmentPreviewBinding
import ru.cactus.jotme.repository.entity.Note
import ru.cactus.jotme.ui.note_edit.NoteEditActivity
import ru.cactus.jotme.ui.notes.NotesFragment
import ru.cactus.jotme.utils.EXTRA_NOTE
import ru.cactus.jotme.utils.FRG_MAIN

/**
 * Экран просмотра заметки без возможности редактирования
 */
class PreviewFragment : Fragment(), PreviewContract.View {
    private var binding: FragmentPreviewBinding? = null
    private var presenter: PreviewPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = PreviewPresenter(this)
        arguments?.let {
            it.getParcelable<Note>(EXTRA_NOTE)?.let { note -> presenter?.saveIntent(note) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPreviewBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding?.apply {
            presenter?.apply {
                tvCardInfoTitle.text = getNote().title
                tvCardInfoBody.text = getNote().body
            }

            ivFragmentBackBtn.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.rv_fragment, NotesFragment::class.java, null, FRG_MAIN)
                    .commit()
            }

            ibFragmentNoteEdit.setOnClickListener {
                presenter?.onEditNoteClick()
            }
        }
    }

    /**
     * Открываем экран редактирования заметки
     */
    override fun startEditNoteActivity() {
        Intent(requireContext(), NoteEditActivity::class.java).apply {
            putExtra(EXTRA_NOTE, presenter?.getNote())
        }.also { startActivity(it) }
    }

    companion object {
        @JvmStatic
        fun newInstance(note: Note): PreviewFragment =
            PreviewFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_NOTE, note)
                }
            }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}
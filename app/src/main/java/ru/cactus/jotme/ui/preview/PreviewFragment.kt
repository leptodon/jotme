package ru.cactus.jotme.ui.preview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import ru.cactus.jotme.R
import ru.cactus.jotme.databinding.FragmentPreviewBinding
import ru.cactus.jotme.repository.entity.Note
import ru.cactus.jotme.ui.main.MainActivity
import ru.cactus.jotme.ui.note_edit.NoteEditActivity
import ru.cactus.jotme.ui.notes.NotesFragment
import ru.cactus.jotme.utils.*

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
            presenter?.saveIntent(it.getString(EXTRA_NOTE).orEmpty())
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
                    .replace(R.id.rv_fragment, NotesFragment::class.java, null)
                    .commit()
            }

            ibFragmentNoteEdit.setOnClickListener {
                presenter?.onEditNoteClick()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        parentFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.rv_fragment, NotesFragment::class.java, null)
            .commit()
    }

    override fun startEditNoteActivity() {
        Intent(requireContext(), NoteEditActivity::class.java).apply {
            putExtra(EXTRA_TITLE, presenter?.getNote()?.title)
            putExtra(EXTRA_BODY, presenter?.getNote()?.body)
        }.also { startActivity(it) }
    }

    companion object {
        @JvmStatic
        fun newInstance(note: Note) =
            PreviewFragment().apply {
                arguments = Bundle().apply {
                    this.putString(EXTRA_NOTE, Gson().toJson(note))
                }
            }
    }
}
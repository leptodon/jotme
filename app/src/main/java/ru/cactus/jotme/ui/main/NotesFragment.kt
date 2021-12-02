package ru.cactus.jotme.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ru.cactus.jotme.R
import ru.cactus.jotme.databinding.NotesListLayoutBinding
import ru.cactus.jotme.repository.entity.Note
import ru.cactus.jotme.ui.adapters.NotesAdapter
import ru.cactus.jotme.ui.note_edit.NoteEditActivity

class NotesFragment : Fragment(R.layout.notes_list_layout), NotesContract.View {
    private var binding: NotesListLayoutBinding? = null
    private var presenter: NotesPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter = NotesPresenter(this)
        binding = NotesListLayoutBinding.inflate(inflater, container, false)
        binding.apply {
            this?.rvNotesList?.layoutManager =
                StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        }
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private val adapter = NotesAdapter(
        onViewClick = { presenter?.onNoteClick(it) },
        onEditClick = { presenter?.onEditBtnNoteClick(it) }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.rvNotesList?.adapter = adapter
        adapter.showList(presenter?.getNotes() ?: emptyList())
    }

    override fun startEditNoteActivity(note: Note) {
        val intentEditNote = Intent(activity?.baseContext, NoteEditActivity::class.java)
        intentEditNote.putExtra("TITLE", note.title)
        intentEditNote.putExtra("BODY", note.body)
        startActivity(intentEditNote)
    }

    override fun startPreviewDialog(note: Note) {
        PreviewDialogFragment(note).show(childFragmentManager, "signature")
    }

}
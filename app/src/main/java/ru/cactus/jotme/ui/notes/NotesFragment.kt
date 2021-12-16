package ru.cactus.jotme.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ru.cactus.jotme.FragmentSwipeContainer
import ru.cactus.jotme.R
import ru.cactus.jotme.databinding.NotesListLayoutBinding
import ru.cactus.jotme.repository.AppDatabase
import ru.cactus.jotme.repository.GetDataImpl
import ru.cactus.jotme.repository.NotesList
import ru.cactus.jotme.repository.db.NotesRepository
import ru.cactus.jotme.repository.entity.Note
import ru.cactus.jotme.repository.model.NotesDataSource
import ru.cactus.jotme.ui.adapters.NotesAdapter
import ru.cactus.jotme.ui.main.ButtonController
import ru.cactus.jotme.ui.preview.PreviewFragment
import ru.cactus.jotme.utils.FRG_PREV
import ru.cactus.jotme.utils.FRG_SWC
import ru.cactus.jotme.utils.SPAN_COUNT

/**
 * RecycleView fragment
 */
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
        initViews()
        return binding?.root
    }

    private fun initViews(){
        binding?.apply {
            rvNotesList.layoutManager =
                StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL)
        }
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as? ButtonController)?.hideNewNoteBtn(true)
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as? ButtonController)?.hideNewNoteBtn(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private val adapter = NotesAdapter(
        onViewClick = {
            parentFragmentManager.beginTransaction()
                .replace(R.id.rv_fragment, FragmentSwipeContainer(it.id?:0), FRG_SWC)
                .commit()
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.rvNotesList?.adapter = adapter
        adapter.setItems(presenter?.getNotes() ?: emptyList())
    }

    override fun startPreviewFragment(note: Note) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.rv_fragment, PreviewFragment.newInstance(note), FRG_PREV)
            .commit()
    }

}
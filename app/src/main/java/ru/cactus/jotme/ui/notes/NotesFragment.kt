package ru.cactus.jotme.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ru.cactus.jotme.R
import ru.cactus.jotme.databinding.NotesListLayoutBinding
import ru.cactus.jotme.repository.AppDatabase
import ru.cactus.jotme.repository.db.NotesRepository
import ru.cactus.jotme.repository.entity.Note
import ru.cactus.jotme.ui.adapters.NotesAdapter
import ru.cactus.jotme.ui.main.ButtonController
import ru.cactus.jotme.ui.swiper.PageSwiperFragment
import ru.cactus.jotme.ui.preview.PreviewFragment
import ru.cactus.jotme.utils.*

/**
 * Класс контейнер для RecyclerView,
 * реализующий отображение карточек заметок с возможностью прокрутки
 */
class NotesFragment : Fragment(R.layout.notes_list_layout), NotesContract.View {
    private var binding: NotesListLayoutBinding? = null
    private var presenter: NotesPresenter? = null
    private lateinit var db: AppDatabase
    private lateinit var notesRepository: NotesRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        db = AppDatabase.getInstance(requireContext())
        notesRepository = NotesRepository(db)

        presenter = NotesPresenter(this, notesRepository)
        binding = NotesListLayoutBinding.inflate(inflater, container, false)
        initViews()
        return binding?.root
    }

    private fun initViews() {
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
        binding = null
        super.onDestroyView()
    }

    private val adapter = NotesAdapter(
        onViewClick = {
            parentFragmentManager.beginTransaction()
                .replace(R.id.rv_fragment, PageSwiperFragment.getInstance(it), FRG_SWC)
                .commit()
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter?.getNotes()
    }

    override fun addListToView(list: List<Note>) {
        binding?.rvNotesList?.adapter = adapter
        adapter.setItems(list)
    }

    override fun startPreviewFragment(note: Note) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.rv_fragment, PreviewFragment.newInstance(note), FRG_PREV)
            .commit()
    }

    override fun onDestroy() {
        presenter?.onDestroy()
        binding = null
        super.onDestroy()
    }
}
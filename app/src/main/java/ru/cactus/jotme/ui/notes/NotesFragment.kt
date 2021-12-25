package ru.cactus.jotme.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ru.cactus.jotme.R
import ru.cactus.jotme.databinding.NotesListLayoutBinding
import ru.cactus.jotme.repository.AppDatabase
import ru.cactus.jotme.repository.db.DatabaseRepository
import ru.cactus.jotme.ui.adapters.NotesAdapter
import ru.cactus.jotme.ui.main.ButtonController
import ru.cactus.jotme.ui.swiper.PageSwiperFragment
import ru.cactus.jotme.utils.FRG_SWC
import ru.cactus.jotme.utils.SPAN_COUNT

/**
 * Класс контейнер для RecyclerView,
 * реализующий отображение карточек заметок с возможностью прокрутки
 */
class NotesFragment : Fragment(R.layout.notes_list_layout) {
    private lateinit var binding: NotesListLayoutBinding
    private lateinit var db: AppDatabase
    private lateinit var databaseRepository: DatabaseRepository
    private lateinit var viewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        db = AppDatabase.getInstance(requireContext())
        databaseRepository = DatabaseRepository(db)

        viewModel = NotesViewModel(databaseRepository)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.notes_list_layout,
            container,
            false
        )
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.apply {
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

    private val adapter = NotesAdapter(
        onViewClick = {
            parentFragmentManager.beginTransaction()
                .replace(R.id.rv_fragment, PageSwiperFragment.getInstance(it), FRG_SWC)
                .commit()
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvNotesList.adapter = adapter
        viewModel.notesList.observe(viewLifecycleOwner) {
            adapter.setItems(it)
        }
    }
}
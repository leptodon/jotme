package ru.cactus.jotme.ui.swiper

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.cactus.jotme.databinding.FragmentSwipeContainerBinding
import ru.cactus.jotme.repository.AppDatabase
import ru.cactus.jotme.repository.db.NotesRepository
import ru.cactus.jotme.repository.entity.Note
import ru.cactus.jotme.ui.preview.PreviewFragment

/**
 * Класс контейнер для viewPager2 фрагментов,
 * реализующий отображение превью заметок с возможностью перелистывания
 * @param pos текущая позиция нажатого элемента в RecyclerView
 * @param notesList список со всеми заметками в RecyclerView
 */
class PageSwiper : Fragment(), PageSwiperContract.View {

    /**
     * Адаптер для ViewPager2
     */
    inner class FragmentSlidePagerAdapter(fr: FragmentActivity) : FragmentStateAdapter(fr) {
        private var list: List<Note> = emptyList()

        override fun getItemCount(): Int = list.size

        override fun createFragment(position: Int): Fragment {
            return PreviewFragment.newInstance(list[position])
        }

        @SuppressLint("NotifyDataSetChanged")
        fun setList(list: List<Note>) {
            this.list = list
            notifyDataSetChanged()
        }
    }

    private var binding: FragmentSwipeContainerBinding? = null
    private var presenter: PageSwiperPresenter? = null
    private lateinit var db: AppDatabase
    private lateinit var notesRepository: NotesRepository
    private var notesList: List<Note> = emptyList()

    override fun addListToView(list: List<Note>) {
        Log.d("FROMDB_IN_FRAGMENT_3", list.toString())
        notesList = list
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        db = AppDatabase.getInstance(requireContext())
        notesRepository = NotesRepository(db)
        presenter = PageSwiperPresenter(this, notesRepository)

        presenter?.getNotesList()
        Log.d("FROMDB_IN_FRAGMENT", notesList.toString())

        val adapter = FragmentSlidePagerAdapter(requireActivity())
        adapter.setList(notesList)

        binding = FragmentSwipeContainerBinding.inflate(inflater, container, false)
        binding?.let { it.pager.adapter = adapter }
        return binding?.root
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}
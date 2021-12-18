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
 * @param globalPosition
 */
class PageSwiper(private val globalPosition:Int) : Fragment(), PageSwiperContract.View {

    /**
     * Адаптер для ViewPager2
     */
    inner class FragmentSlidePagerAdapter(fr: FragmentActivity) : FragmentStateAdapter(fr) {
        private var currentList: List<Note> = emptyList()

        override fun getItemCount(): Int = currentList.size

        override fun createFragment(position: Int): Fragment {

            Log.d("PAGE_SWIPER", "$globalPosition = globalPosition позиция открытого фрагмента из RecyclerView")
            Log.d("PAGE_SWIPER", "$position = position позиция назначеная фрагменту ViewPager2")

            return PreviewFragment.newInstance(currentList[position])
        }

        @SuppressLint("NotifyDataSetChanged")
        fun setList(list: List<Note>) {
            currentList = list
            notifyDataSetChanged()
        }
    }

    private var binding: FragmentSwipeContainerBinding? = null
    private var presenter: PageSwiperPresenter? = null
    private lateinit var db: AppDatabase
    private lateinit var notesRepository: NotesRepository
    private var notesList: List<Note> = emptyList()
    private lateinit var adapter: FragmentSlidePagerAdapter

    override fun addListToView(list: List<Note>) {
        notesList = list
        adapter.setList(notesList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        db = AppDatabase.getInstance(requireContext())
        notesRepository = NotesRepository(db)
        presenter = PageSwiperPresenter(this, notesRepository)

        binding = FragmentSwipeContainerBinding.inflate(inflater, container, false)
        adapter = FragmentSlidePagerAdapter(requireActivity())
        binding?.let { it.pager.adapter = adapter }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter?.getNotesList()
    }

    override fun onDestroy() {
        presenter?.onDestroy()
        binding = null
        super.onDestroy()
    }

}
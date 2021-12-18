package ru.cactus.jotme.ui.swiper

import android.annotation.SuppressLint
import android.os.Bundle
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
import ru.cactus.jotme.utils.ARG_POSITION
import kotlin.properties.Delegates

/**
 * Класс контейнер для viewPager2 фрагментов,
 * реализующий отображение превью заметок с возможностью перелистывания
 */
class PageSwiper : Fragment(), PageSwiperContract.View {

    /**
     * Адаптер для ViewPager2
     */
    inner class FragmentSlidePagerAdapter(fr: FragmentActivity) : FragmentStateAdapter(fr) {
        private var currentList: List<Note> = emptyList()

        override fun getItemCount(): Int = currentList.size

        override fun createFragment(position: Int): Fragment {
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
    private lateinit var adapter: FragmentSlidePagerAdapter
    private var localPosition by Delegates.notNull<Int>()

    override fun addListToView(list: List<Note>) {
        adapter.setList(list)
        binding?.pager?.setCurrentItem(localPosition, true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSwipeContainerBinding.inflate(inflater, container, false)
        .also { this.binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        localPosition = arguments?.getInt(ARG_POSITION) ?: 0
        db = AppDatabase.getInstance(requireContext())
        notesRepository = NotesRepository(db)
        presenter = PageSwiperPresenter(this, notesRepository)
        adapter = FragmentSlidePagerAdapter(requireActivity())
        binding?.let { it.pager.adapter = adapter }
        presenter?.getNotesList()
    }

    override fun onDestroy() {
        presenter?.onDestroy()
        binding = null
        super.onDestroy()
    }

    companion object {
        fun getInstance(notePosition: Int) =
            PageSwiper().apply {
                arguments = Bundle().apply { putInt(ARG_POSITION, notePosition) }
            }
    }
}
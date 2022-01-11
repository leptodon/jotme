package ru.cactus.jotme.ui.swiper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.cactus.jotme.R
import ru.cactus.jotme.databinding.FragmentSwipeContainerBinding
import ru.cactus.jotme.repository.AppDatabase
import ru.cactus.jotme.repository.db.DatabaseRepositoryImpl
import ru.cactus.jotme.ui.adapters.FragmentSlidePagerAdapter
import ru.cactus.jotme.utils.ARG_POSITION
import kotlin.properties.Delegates

/**
 * Класс контейнер для viewPager2 фрагментов,
 * реализующий отображение превью заметок с возможностью перелистывания
 */
class PageSwiperFragment : Fragment() {

    private lateinit var binding: FragmentSwipeContainerBinding
    private lateinit var db: AppDatabase
    private lateinit var databaseRepositoryImpl: DatabaseRepositoryImpl
    private lateinit var adapter: FragmentSlidePagerAdapter
    private var localPosition by Delegates.notNull<Int>()
    private lateinit var viewModel: PageSwiperViewModel

    private fun initObservers() {
        viewModel.notesList.observe(viewLifecycleOwner) {
            adapter.setList(it)
            binding.pager.setCurrentItem(localPosition, true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DataBindingUtil.inflate<FragmentSwipeContainerBinding>(
        inflater,
        R.layout.fragment_swipe_container,
        container, false
    ).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        localPosition = arguments?.getInt(ARG_POSITION) ?: 0
        db = AppDatabase.getInstance(requireContext())
        databaseRepositoryImpl = DatabaseRepositoryImpl(db)
        viewModel = PageSwiperViewModel(databaseRepositoryImpl)

        adapter = FragmentSlidePagerAdapter(requireActivity())
        binding.pager.adapter = adapter

        initObservers()
    }

    companion object {
        fun getInstance(notePosition: Int): PageSwiperFragment =
            PageSwiperFragment().apply {
                arguments = Bundle().apply { putInt(ARG_POSITION, notePosition) }
            }
    }
}
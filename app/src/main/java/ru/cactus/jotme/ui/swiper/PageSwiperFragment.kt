package ru.cactus.jotme.ui.swiper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.android.support.AndroidSupportInjection
import ru.cactus.jotme.R
import ru.cactus.jotme.app.App
import ru.cactus.jotme.app.appComponent
import ru.cactus.jotme.databinding.FragmentSwipeContainerBinding
import ru.cactus.jotme.di.AppComponent
import ru.cactus.jotme.ui.adapters.FragmentSlidePagerAdapter
import ru.cactus.jotme.utils.ARG_POSITION
import kotlin.properties.Delegates

/**
 * Класс контейнер для viewPager2 фрагментов,
 * реализующий отображение превью заметок с возможностью перелистывания
 */
class PageSwiperFragment : Fragment() {

    private lateinit var binding: FragmentSwipeContainerBinding
    private lateinit var adapter: FragmentSlidePagerAdapter
    private var localPosition by Delegates.notNull<Int>()

    private val viewModel: PageSwiperViewModel by viewModels {
        getAppComponent().pageSwiperViewModelFactory()
    }

    private fun Fragment.getAppComponent(): AppComponent = requireContext().appComponent

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
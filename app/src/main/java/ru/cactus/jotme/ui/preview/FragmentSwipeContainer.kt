package ru.cactus.jotme.ui.preview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.cactus.jotme.databinding.FragmentSwipeContainerBinding
import ru.cactus.jotme.repository.entity.Note


class FragmentSwipeContainer(private val pos:Int, private val notesList: List<Note>) : Fragment() {

    private var binding: FragmentSwipeContainerBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val adapter = FragmentSlidePagerAdapter(requireActivity())
        binding = FragmentSwipeContainerBinding.inflate(inflater, container, false)
        binding!!.pager.adapter = adapter
        return binding?.root
    }

    inner class FragmentSlidePagerAdapter(fr: FragmentActivity) : FragmentStateAdapter(fr) {
        override fun getItemCount(): Int = notesList.size-pos

        override fun createFragment(position: Int): Fragment {
            val currentPosition = position+pos
            return PreviewFragment.newInstance(notesList[currentPosition])
        }
    }
}
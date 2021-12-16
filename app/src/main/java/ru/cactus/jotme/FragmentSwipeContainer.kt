package ru.cactus.jotme

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.cactus.jotme.databinding.FragmentSwipeContainerBinding
import ru.cactus.jotme.repository.NotesList
import ru.cactus.jotme.ui.preview.PreviewFragment


class FragmentSwipeContainer(private val pos:Int) : Fragment() {

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
        override fun getItemCount(): Int = NotesList().getNotes().size

        override fun createFragment(position: Int): Fragment {
            Log.d("TAG", "Fragment position in adapter is $position")
            Log.d("TAG", "Fragment position in list is $pos")
            Log.d("TAG", "Fragment NEW position in swipe is ${itemCount-position}")
            val currentPosition = position+pos
            return if(currentPosition < NotesList().getNotes().size){
                PreviewFragment.newInstance(NotesList().getNotes()[currentPosition])
            } else {
                PreviewFragment.newInstance(NotesList().getNotes()[position])
            }
        }

    }
}
package ru.cactus.jotme.ui.adapters

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.cactus.jotme.repository.entity.Note
import ru.cactus.jotme.ui.preview.PreviewFragment


/**
 * Адаптер для ViewPager2
 */
class FragmentSlidePagerAdapter(fr: FragmentActivity) : FragmentStateAdapter(fr) {
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
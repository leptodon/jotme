package ru.cactus.jotme.ui.adapters

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.cactus.jotme.data.repository.db.entity.DbNote
import ru.cactus.jotme.ui.preview.PreviewFragment


/**
 * Адаптер для ViewPager2
 */
class FragmentSlidePagerAdapter(fr: FragmentActivity) : FragmentStateAdapter(fr) {
    private var currentList: List<DbNote> = emptyList()

    /**
     * Возвращаем количество элементов
     */
    override fun getItemCount(): Int = currentList.size

    /**
     * Создание превью фрагмента с передачей позиции из общего списка
     * @param position позиция элемента в общем списке recyclerView
     */
    override fun createFragment(position: Int): Fragment {
        return PreviewFragment.newInstance(currentList[position])
    }

    /**
     * Передача списка заметок
     * @param list список всех заметок
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<DbNote>) {
        currentList = list
        notifyDataSetChanged()
    }
}
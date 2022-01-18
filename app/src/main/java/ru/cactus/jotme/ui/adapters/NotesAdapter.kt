package ru.cactus.jotme.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.cactus.jotme.R
import ru.cactus.jotme.databinding.NotesItemLayoutBinding
import ru.cactus.jotme.data.repository.db.entity.DbNote


/**
 * Адаптер для хранения и отображения заметок
 * @param onViewClick callBack нажатия на карточку в recyclerView
 */
class NotesAdapter(
    private val onViewClick: (Int) -> Unit
) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private var items: List<DbNote> = emptyList()

    /**
     * Холдер для элементов во RecyclerView
     * @param binding layout для элемента в списке
     * @param onViewClick CallBack нажатия на элемент в списке
     */
    inner class NoteViewHolder(
        private val binding: NotesItemLayoutBinding,
        private val onViewClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Сеттинг данных во View
         */
        fun bind(dbNote: DbNote) {
            val shortBody = with(StringBuilder()) {
                if (dbNote.body.length > 30) {
                    append(dbNote.body.substring(0..30))
                    append("...")
                } else {
                    append(dbNote.body)
                }
                toString()
            }
            with(binding) {
                tvCardTitle.text = dbNote.title
                tvCardText.text = shortBody

                root.setOnClickListener {
                    onViewClick.invoke(items.indexOf(dbNote))
                }
            }
        }
    }

    /**
     * Получаем список заметок для заполнени recyclerView
     * @param list список заметок
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setItems(list: List<DbNote>) {
        items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemBinding =
            DataBindingUtil.inflate<NotesItemLayoutBinding>(
                LayoutInflater.from(parent.context),
                R.layout.notes_item_layout, parent, false
            )
        return NoteViewHolder(itemBinding, onViewClick)
    }

    override fun onBindViewHolder(holderNote: NoteViewHolder, position: Int) {
        val item = items[position]
        holderNote.bind(item)
    }

    override fun getItemCount(): Int = items.size
}
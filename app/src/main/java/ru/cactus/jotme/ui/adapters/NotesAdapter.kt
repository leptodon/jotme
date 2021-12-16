package ru.cactus.jotme.ui.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.cactus.jotme.databinding.NotesItemLayoutBinding
import ru.cactus.jotme.repository.entity.Note

class NotesAdapter(
    private val onViewClick: (Note) -> Unit
) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private var items:List<Note> = emptyList()

    inner class NoteViewHolder(
        private val binding: NotesItemLayoutBinding,
        private val onViewClick: (Note) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            val shortBody = with(StringBuilder()) {
                if (note.body.length > 30) {
                    append(note.body.substring(0..30))
                    append("...")
                } else {
                    append(note.body)
                }
                toString()
            }
            with(binding) {
                tvCardTitle.text = note.title
                tvCardText.text = shortBody

                root.setOnClickListener {
                    onViewClick.invoke(note)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(list: List<Note>) {
        items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemBinding =
            NotesItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(itemBinding, onViewClick)
    }

    override fun onBindViewHolder(holderNote: NoteViewHolder, position: Int) {
        val item = items[position]
//        item.id = position
        holderNote.bind(item)
    }

    override fun getItemCount() = items.size
}
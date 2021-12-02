package ru.cactus.jotme.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.cactus.jotme.databinding.NotesItemLayoutBinding
import ru.cactus.jotme.repository.entity.Note

class NotesAdapter(private val onViewClick: (Note) -> Unit,
                   private val onEditClick: (Note) -> Unit) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val items = mutableListOf<Note>()

    inner class NoteViewHolder(
        private val binding: NotesItemLayoutBinding,
        private val onViewClick: (Note) -> Unit,
        private val onEditClick: (Note) -> Unit
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

                ibCardEdit.setOnClickListener {
                    onEditClick.invoke(note)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun showList(list: List<Note>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemBinding =
            NotesItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(itemBinding, onViewClick, onEditClick)
    }

    override fun onBindViewHolder(holderNote: NoteViewHolder, position: Int) {
        val item = items[position]
        holderNote.bind(item)
    }

    override fun getItemCount() = items.size
}
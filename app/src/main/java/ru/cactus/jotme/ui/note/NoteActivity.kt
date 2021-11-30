package ru.cactus.jotme.ui.note

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.cactus.jotme.R
import ru.cactus.jotme.databinding.NewNoteActivityBinding
import ru.cactus.jotme.repository.entity.Note

/**
 * Экран редактирования заметки
 */
class NoteActivity : AppCompatActivity(), NoteContract.View {
    private var binding: NewNoteActivityBinding? = null
    private var presenter: NotePresenter? = null
    private lateinit var mSetting: SharedPreferences
    private lateinit var notes: List<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NewNoteActivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        mSetting = getPreferences(Context.MODE_PRIVATE)
        presenter = NotePresenter(mSetting, this)
    }

    override fun onStart() {
        initView()
        super.onStart()
    }

    private fun initView() {
        binding?.apply {
            notes = presenter?.getAllNotes() ?: emptyList()

            if (presenter?.checkNote() == false) {
                etNoteTitle.setText(notes[0].title)
                etNoteBody.setText(notes[0].body)
            } else {
                etNoteTitle.setText("")
                etNoteBody.setText("")
            }

            ivBackBtn.setOnClickListener {
                onBackPressed()
            }

            ibShare.setOnClickListener {
                shareNote(
                    Note(
                        0,
                        etNoteTitle.text.toString(),
                        etNoteBody.text.toString()
                    )
                )
            }
        }
    }

    override fun showSaveToast() {
        Toast.makeText(applicationContext, R.string.save_note, Toast.LENGTH_SHORT).show()
    }

    override fun showDeleteToast() {
        Toast.makeText(applicationContext, R.string.delete_note, Toast.LENGTH_SHORT).show()
    }

    override fun shareNote(note: Note) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, note.toString())
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    override fun onBackPressed() {
        presenter?.addNewNote(
            binding?.etNoteTitle?.text.toString(),
            binding?.etNoteBody?.text.toString()
        )
        super.onBackPressed()
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}
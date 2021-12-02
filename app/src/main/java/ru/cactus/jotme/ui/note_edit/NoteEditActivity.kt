package ru.cactus.jotme.ui.note_edit

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
class NoteEditActivity : AppCompatActivity(), NoteEditContract.View {
    private var binding: NewNoteActivityBinding? = null
    private var editPresenter: NoteEditPresenter? = null
    private lateinit var mSetting: SharedPreferences

    //    private lateinit var notes: List<Note>
    private lateinit var note: Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NewNoteActivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        mSetting = getPreferences(Context.MODE_PRIVATE)
        editPresenter = NoteEditPresenter(mSetting, this)
    }

    override fun onStart() {
        super.onStart()
        initView()

        //TODO Не работает получение данных из интента intent = null
        note = Note(
            0,
            intent?.getStringExtra("TITLE") ?: "",
            intent?.getStringExtra("BODY") ?: ""
        )

    }

    private fun initView() {
        binding?.apply {
//            notes = editPresenter?.getAllNotes() ?: emptyList()

            if (editPresenter?.checkNote() == false) {
                etNoteTitle.setText(note.title)
                etNoteBody.setText(note.body)
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
        editPresenter?.addNewNote(
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
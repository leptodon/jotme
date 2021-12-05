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
import ru.cactus.jotme.utils.EXTRA_BODY
import ru.cactus.jotme.utils.EXTRA_TITLE


/**
 * Экран редактирования заметки
 */
class NoteEditActivity : AppCompatActivity(), NoteEditContract.View {
    private var binding: NewNoteActivityBinding? = null
    private var presenter: NoteEditPresenter? = null
    private lateinit var mSetting: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NewNoteActivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val myIntent = intent.extras

        mSetting = getPreferences(Context.MODE_PRIVATE)
        presenter = NoteEditPresenter(mSetting, this)

        presenter?.saveIntent(
            Note(
                0,
                myIntent?.getString(EXTRA_TITLE).orEmpty(),
                myIntent?.getString(EXTRA_BODY).orEmpty()
            )
        )

    }

    override fun onStart() {
        super.onStart()
        initView()
    }

    private fun initView() {
        val note = presenter?.getNote()

        binding?.apply {

            etNoteTitle.setText(note?.title)
            etNoteBody.setText(note?.body)

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
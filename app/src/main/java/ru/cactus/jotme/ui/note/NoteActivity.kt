package ru.cactus.jotme.ui.note

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.new_note_activity.*
import ru.cactus.jotme.R
import ru.cactus.jotme.repository.entity.Note


class NoteActivity : AppCompatActivity(), NoteContract.View {

    private var presenter: NotePresenter? = null
    private lateinit var mSetting: SharedPreferences
    private lateinit var notes: List<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_note_activity)
        mSetting = getPreferences(Context.MODE_PRIVATE)
        presenter = NotePresenter(this)

        initView()
    }

    override fun onStart() {
        notes = presenter?.getAllNotes(mSetting)!!

        et_note_title.setText(notes[0].title)
        et_note_body.setText(notes[0].body)

        super.onStart()
    }

    private fun initView() {
        iv_back_btn.setOnClickListener {
            presenter?.addNewNote(
                mSetting,
                et_note_title.text.toString(),
                et_note_body.text.toString()
            )
            onBackPressed()
        }

        ib_share.setOnClickListener {
            presenter!!.shareNote(
                Note(
                    0,
                    et_note_title.text.toString(),
                    et_note_body.text.toString()
                )
            )
        }
    }

    override fun updateViewData() {
    }

    override fun showSaveToast() {
        Toast.makeText(applicationContext, "Note was save...", Toast.LENGTH_SHORT).show()
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
        presenter?.addNewNote(mSetting, et_note_title.text.toString(), et_note_body.text.toString())
        super.onBackPressed()
    }

}
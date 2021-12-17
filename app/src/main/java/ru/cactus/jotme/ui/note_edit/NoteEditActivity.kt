package ru.cactus.jotme.ui.note_edit

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.cactus.jotme.R
import ru.cactus.jotme.databinding.NewNoteActivityBinding
import ru.cactus.jotme.repository.AppDatabase
import ru.cactus.jotme.repository.db.NotesRepository
import ru.cactus.jotme.repository.entity.Note
import ru.cactus.jotme.ui.dialogs.SaveDialogFragment
import ru.cactus.jotme.ui.main.MainActivity
import ru.cactus.jotme.utils.EXTRA_NOTE


/**
 * Экран редактирования заметки
 */
class NoteEditActivity : AppCompatActivity(), NoteEditContract.View {
    private var binding: NewNoteActivityBinding? = null
    private var presenter: NoteEditPresenter? = null
    private lateinit var mSetting: SharedPreferences
    private lateinit var db: AppDatabase
    private lateinit var notesRepository: NotesRepository
    private var note: Note? = null
    private lateinit var intentNewNote: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NewNoteActivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbar)
        supportActionBar?.title = ""

        db = AppDatabase.getInstance(applicationContext)

        notesRepository = NotesRepository(db)
        intentNewNote = Intent(this@NoteEditActivity, MainActivity::class.java)
        note = intent.extras?.getParcelable(EXTRA_NOTE)

        mSetting = getPreferences(Context.MODE_PRIVATE)
        presenter = NoteEditPresenter(this, notesRepository)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_save) {
            SaveDialogFragment().show(supportFragmentManager, "TAG")
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return true
    }

    override fun onStart() {
        super.onStart()
        initView()
    }

    private fun initView() {
        binding?.apply {
            ivBackBtn.setOnClickListener {
                onBackPressed()
            }
            if (note != null) {
                etNoteTitle.setText(note!!.title)
                etNoteBody.setText(note!!.body)

                ibShare.setOnClickListener {
                    shareNote(
                        Note(
                            null,
                            etNoteTitle.text.toString(),
                            etNoteBody.text.toString()
                        )
                    )
                }

                ibDelete.setOnClickListener {
                    note?.id?.let { id -> presenter?.deleteNote(id) }
                    startActivity(intentNewNote)
                }
            }
        }
    }

    override fun showSaveToast() {
        Toast.makeText(applicationContext, R.string.save_note, Toast.LENGTH_SHORT).show()
    }

    override fun showDeleteToast() {
        Toast.makeText(applicationContext, R.string.delete_note, Toast.LENGTH_SHORT).show()
    }

    override fun shareNote(note: Note?) {
        if (note != null) {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, note.toString())
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    fun saveNote() {
        binding?.apply {
            when {
                note == null -> {
                    presenter?.takeIf { etNoteTitle.text?.isNotEmpty() ?: false || etNoteBody.text?.isNotEmpty() ?: false }
                        ?.addNewNote(null, etNoteTitle.text.toString(), etNoteBody.text.toString())
                }
                note != null -> {
                    presenter?.apply {
                        addNewNote(
                            note!!.id,
                            etNoteTitle.text.toString(),
                            etNoteBody.text.toString()
                        )
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        saveNote()
        startActivity(intentNewNote)
        super.onBackPressed()
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}
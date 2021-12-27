package ru.cactus.jotme.ui.note_edit

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.cactus.jotme.R
import ru.cactus.jotme.databinding.NewNoteActivityBinding
import ru.cactus.jotme.repository.AppDatabase
import ru.cactus.jotme.repository.db.DatabaseRepository
import ru.cactus.jotme.repository.entity.Note
import ru.cactus.jotme.ui.dialogs.SaveDialogFragment
import ru.cactus.jotme.ui.main.MainActivity
import ru.cactus.jotme.utils.EXTRA_NOTE


/**
 * Экран редактирования заметки
 */
class NoteEditActivity : AppCompatActivity() {
    private lateinit var binding: NewNoteActivityBinding
    private lateinit var db: AppDatabase
    private lateinit var databaseRepository: DatabaseRepository
    private var note: Note? = null
    private lateinit var intentNewNote: Intent
    private lateinit var viewModel: NoteEditViewModel
    private lateinit var saveDialog: SaveDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.new_note_activity)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""

        db = AppDatabase.getInstance(applicationContext)
        databaseRepository = DatabaseRepository(db)
        viewModel = NoteEditViewModel(databaseRepository)
        saveDialog = SaveDialogFragment()

        intentNewNote = Intent(this@NoteEditActivity, MainActivity::class.java)
        note = intent.extras?.getParcelable(EXTRA_NOTE)
    }

    /**
     * Обработчик выбора и нажатия на кнопку "save" в toolbar
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_save) {
            saveDialog.apply {
                arguments = Bundle().apply {
                    putParcelable(
                        EXTRA_NOTE, Note(
                            note?.id,
                            binding.etNoteTitle.text.toString(),
                            binding.etNoteBody.text.toString()
                        )
                    )
                }
            }.show(supportFragmentManager, "TAG")
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
        initObserver()
    }

    private fun initView() {
        binding.apply {
            ivBackBtn.setOnClickListener {
                onBackPressed()
            }
            note?.let { currentNote ->
                etNoteTitle.setText(currentNote.title)
                etNoteBody.setText(currentNote.body)

                ibShare.setOnClickListener {
                    shareNote(currentNote)
                }

                ibDelete.setOnClickListener {
                    currentNote.id?.let { id -> viewModel.deleteNote(id) }
                    startActivity(intentNewNote)
                }
            }
        }
    }

    private fun initObserver() {
        with(viewModel) {
            showSaveToast.observe(this@NoteEditActivity) {
                if (it) {
                    showSaveToast()
                }
            }

            showDeleteToast.observe(this@NoteEditActivity) {
                if (it) {
                    showDeleteToast()
                }
            }
        }

        saveDialog.saveNote.observe(this) {
            viewModel.saveNote(it.id, it.title, it.body)
        }
    }

    private fun showSaveToast() {
        Toast.makeText(applicationContext, R.string.save_note, Toast.LENGTH_SHORT).show()
        viewModel.setNoteSave()
    }

    private fun showDeleteToast() {
        Toast.makeText(applicationContext, R.string.delete_note, Toast.LENGTH_SHORT).show()
        viewModel.setNoteDelete()
    }

    private fun shareNote(note: Note?) {
        note?.let {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, note.toString())
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    override fun onBackPressed() {
        startActivity(intentNewNote)
        super.onBackPressed()
    }

}
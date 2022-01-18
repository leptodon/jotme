package ru.cactus.jotme.ui.note_edit

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.cactus.jotme.R
import ru.cactus.jotme.data.repository.AppDatabase
import ru.cactus.jotme.data.repository.db.DatabaseRepository
import ru.cactus.jotme.data.repository.db.DatabaseRepositoryImpl
import ru.cactus.jotme.data.repository.db.entity.DbNote
import ru.cactus.jotme.data.repository.network.NetworkRepository
import ru.cactus.jotme.data.repository.network.NetworkResult
import ru.cactus.jotme.data.repository.network.RetrofitBuilder
import ru.cactus.jotme.databinding.NewNoteActivityBinding
import ru.cactus.jotme.domain.entity.Note
import ru.cactus.jotme.ui.dialogs.SaveDialogFragment
import ru.cactus.jotme.ui.main.MainActivity
import ru.cactus.jotme.utils.*

/**
 * Экран редактирования заметки
 */
class NoteEditActivity : AppCompatActivity() {
    private lateinit var binding: NewNoteActivityBinding
    private lateinit var db: AppDatabase
    private lateinit var databaseRepository: DatabaseRepository
    private lateinit var networkRepository: NetworkRepository
    private var dbNote: DbNote? = null
    private lateinit var intentNewNote: Intent
    private lateinit var viewModel: NoteEditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.new_note_activity)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""

        db = AppDatabase.getInstance(applicationContext)
        databaseRepository = DatabaseRepositoryImpl(db)
        networkRepository = NetworkRepository(RetrofitBuilder.apiService)

        viewModel = NoteEditViewModel(databaseRepository, networkRepository)

        intentNewNote = Intent(this@NoteEditActivity, MainActivity::class.java)
        dbNote = intent.extras?.getParcelable(EXTRA_NOTE)

    }

    /**
     * Обработчик выбора и нажатия на кнопку "save" в toolbar
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save -> {
                SaveDialogFragment().show(supportFragmentManager, "TAG")
            }
            R.id.action_download -> {
                viewModel.fetchResponse()
            }
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
            dbNote?.let { currentNote ->
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
            showSaveToast.observe(this@NoteEditActivity) { showSaveToast() }
            showDeleteToast.observe(this@NoteEditActivity) { showDeleteToast() }
            networkResponse.observe(this@NoteEditActivity) { getNoteFromNetwork(it) }
        }

        supportFragmentManager.setFragmentResultListener(FRG_BUNDLE_SAVE, this) { _, bundle ->
            if (bundle.getBoolean(FRG_SDF_SAVE)) saveCurrentNote()
        }
    }

    private fun saveCurrentNote() {
        with(binding) {
            dbNote = dbNote?.copy(
                title = etNoteTitle.text.toString(),
                body = etNoteBody.text.toString()
            ) ?: DbNote(null, etNoteTitle.text.toString(), etNoteBody.text.toString())
        }
        this.sendBroadcast(Intent().apply {
            action = ACTION
            putExtra(EXTRA_NOTE, dbNote.toString())
        })
        viewModel.saveNote(dbNote?.id, dbNote?.title ?: "", dbNote?.body ?: "")
    }

    private fun showSaveToast() {
        Toast.makeText(applicationContext, R.string.save_note, Toast.LENGTH_SHORT).show()
    }

    private fun showDeleteToast() {
        Toast.makeText(applicationContext, R.string.delete_note, Toast.LENGTH_SHORT).show()
    }

    private fun shareNote(dbNote: DbNote?) {
        dbNote?.let {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, dbNote.toString())
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

    private fun getNoteFromNetwork(networkResult: NetworkResult<Note>) {
        when (networkResult) {
            is NetworkResult.Success -> {
                binding.etNoteTitle.setText(networkResult.data.title)
                binding.etNoteBody.setText(networkResult.data.body)
                Toast.makeText(
                    applicationContext,
                    resources.getString(R.string.load_success),
                    Toast.LENGTH_LONG
                ).show()
            }
            is NetworkResult.Error -> {
                Toast.makeText(
                    applicationContext,
                    resources.getString(R.string.load_error),
                    Toast.LENGTH_LONG
                ).show()
            }
            is NetworkResult.Loading -> {
                Toast.makeText(
                    applicationContext,
                    resources.getString(R.string.load_loading),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
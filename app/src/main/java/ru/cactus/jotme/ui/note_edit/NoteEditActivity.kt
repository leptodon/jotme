package ru.cactus.jotme.ui.note_edit

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.text.toSpanned
import androidx.databinding.DataBindingUtil
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import ru.cactus.jotme.R
import ru.cactus.jotme.data.repository.AppDatabase
import ru.cactus.jotme.data.repository.db.DatabaseRepository
import ru.cactus.jotme.data.repository.db.DatabaseRepositoryImpl
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
    private var note: Note? = null
    private lateinit var intentNewNote: Intent
    private lateinit var viewModel: NoteEditViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.new_note_activity)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        db = AppDatabase.getInstance(applicationContext)
        databaseRepository = DatabaseRepositoryImpl(db)
        networkRepository = NetworkRepository(RetrofitBuilder.apiService)

        viewModel = NoteEditViewModel(databaseRepository, networkRepository)

        intentNewNote = Intent(this@NoteEditActivity, MainActivity::class.java)
        note = intent.extras?.getParcelable(EXTRA_NOTE)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            99
        )

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
            note?.let { currentNote ->
                etNoteTitle.setText(currentNote.title)
                etNoteBody.setText(Html.fromHtml(currentNote.body))

                ibShare.setOnClickListener {
                    shareNote(currentNote)
                }

                ibDelete.setOnClickListener {
                    currentNote.id?.let { id -> viewModel.deleteNote(id) }
                    startActivity(intentNewNote)
                }
            }
            ibBoldText.setOnClickListener { setFormattedText("bold") }
            ibItalicText.setOnClickListener { setFormattedText("italic") }
            ibUnderlinedText.setOnClickListener { setFormattedText("underlined") }
            ibClear.setOnClickListener {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        if (location != null) setLocationText(location)
                    }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setLocationText(location: Location) {
        val bodyText = binding.etNoteBody.text
        val locStr = "${location.latitude},${location.longitude}"
        val htmlText = Html.toHtml(bodyText).toString().replace("</p>", "<br>$locStr</p>")
        binding.etNoteBody.setText(Html.fromHtml(htmlText))
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
            note = note?.copy(
                title = etNoteTitle.text.toString(),
                body = Html.toHtml(
                    binding.etNoteBody.text?.toSpanned()
                ).toString()
            ) ?: Note(null, etNoteTitle.text.toString(), etNoteBody.text.toString())
        }
        this.sendBroadcast(Intent().apply {
            action = ACTION
            putExtra(EXTRA_NOTE, note.toString())
        })
        viewModel.saveNote(note?.id, note?.title ?: "", note?.body ?: "")
    }

    private fun showSaveToast() {
        Toast.makeText(applicationContext, R.string.save_note, Toast.LENGTH_SHORT).show()
    }

    private fun showDeleteToast() {
        Toast.makeText(applicationContext, R.string.delete_note, Toast.LENGTH_SHORT).show()
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

    private fun setHtmlText(text: String, newText: String) {
        if (text.isNotEmpty()) {
            binding.etNoteBody.text = Html.fromHtml(
                Html.toHtml(
                    binding.etNoteBody.text?.toSpanned()
                ).toString().replace(text, newText)
            ) as Editable
        }
    }

    private fun setFormattedText(format: String) {
        val start = binding.etNoteBody.selectionStart
        val end = binding.etNoteBody.selectionEnd
        val text = binding.etNoteBody.text?.subSequence(start, end).toString()

        val rawText = Html.toHtml(
            binding.etNoteBody.text?.toSpanned()
        ).toString()

        if (rawText.contains(formatString(text, format), ignoreCase = true)) {
            if (text.isNotEmpty()) {
                binding.etNoteBody.text = Html.fromHtml(
                    Html.toHtml(
                        binding.etNoteBody.text?.toSpanned()
                    ).toString().replace(formatString(text, format), text)
                ) as Editable
            }
        } else {
            setHtmlText(text, formatString(text, format))
        }
    }

    private fun formatString(text: String, format: String): String = when (format) {
        "bold" -> "<b>${text}</b>"
        "italic" -> "<i>${text}</i>"
        "underlined" -> "<u>${text}</u>"
        else -> text
    }
}
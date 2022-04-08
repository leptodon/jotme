package ru.cactus.jotme.ui.note_edit

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.toSpanned
import androidx.databinding.DataBindingUtil
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.snackbar.Snackbar
import ru.cactus.jotme.R
import ru.cactus.jotme.app.featureComponent
import ru.cactus.jotme.data.repository.network.NetworkResult
import ru.cactus.jotme.databinding.NewNoteActivityBinding
import ru.cactus.jotme.domain.entity.Note
import ru.cactus.jotme.ui.dialogs.SaveDialogFragment
import ru.cactus.jotme.ui.main.MainActivity
import ru.cactus.jotme.utils.*

const val PERMISSION_REQUEST_LOCATION: Int = 1000

/**
 * Экран редактирования заметки
 */
class NoteEditActivity : AppCompatActivity() {

    private lateinit var binding: NewNoteActivityBinding
    private var note: Note? = null
    private lateinit var intentNewNote: Intent
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val viewModel: NoteEditViewModel by viewModels {
        featureComponent.noteEditViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.new_note_activity)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        intentNewNote = Intent(this@NoteEditActivity, MainActivity::class.java)
        note = intent.extras?.getParcelable(EXTRA_NOTE)
    }

    private fun blinkAnimate(view: View) {
        val valueAnimator = AnimatorInflater.loadAnimator(
            this,
            R.animator.blink
        ) as ValueAnimator
        valueAnimator.addUpdateListener { animatorValue ->
            view.alpha = animatorValue.animatedValue as Float
        }
        valueAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                binding.etNoteBody.alpha = 1.0f
            }
        })
        valueAnimator.start()
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
                etNoteBody.setText(HtmlCompat.fromHtml(currentNote.body))

                ibShare.setOnClickListener {
                    shareNote(currentNote)
                }

                ibDelete.setOnClickListener {
                    currentNote.id?.let { id -> viewModel.deleteNote(id) }
                    startActivity(intentNewNote)
                }
            }

            ibBoldText.setOnClickListener { setFormattedText(Font.BOLD) }
            ibItalicText.setOnClickListener { setFormattedText(Font.ITALIC) }
            ibUnderlinedText.setOnClickListener { setFormattedText(Font.UNDERLINED) }
            ibGeo.setOnClickListener {
                checkGeoPermission()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setLocationText(location: Location) {
        val bodyText = binding.etNoteBody.text
        val locStr = "${location.latitude},${location.longitude}"
        val htmlText = HtmlCompat.toHtml(bodyText).replace("</p>", "<br>$locStr</p>")
        binding.etNoteBody.setText(HtmlCompat.fromHtml(htmlText))
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
                body = HtmlCompat.toHtml(
                    binding.etNoteBody.text?.toSpanned()
                )
            ) ?: Note(null, etNoteTitle.text.toString(), etNoteBody.text.toString())
        }

        this.sendBroadcast(Intent().apply {
            action = ACTION
            putExtra(EXTRA_NOTE, note.toString())
        })

        viewModel.saveNote(note?.id, note?.title ?: "", note?.body ?: "")

        blinkAnimate(binding.etNoteBody)
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
            binding.etNoteBody.text = HtmlCompat.fromHtml(
                HtmlCompat.toHtml(
                    binding.etNoteBody.text?.toSpanned()
                ).replace(text, newText)
            ) as Editable
        }
    }

    private fun setFormattedText(format: Font) {
        val start = binding.etNoteBody.selectionStart
        val end = binding.etNoteBody.selectionEnd
        val text = binding.etNoteBody.text?.subSequence(start, end).toString()

        val rawText = HtmlCompat.toHtml(
            binding.etNoteBody.text?.toSpanned()
        )
        Log.d("FORMAT", rawText)

        if (rawText.contains(formatString(text, format), ignoreCase = true)) {
            setHtmlText(formatString(text, format), text)
        } else {
            setHtmlText(text, formatString(text, format))
        }
    }

    private fun formatString(text: String, format: Font): String = when (format) {
        Font.BOLD -> "<b>${text}</b>"
        Font.ITALIC -> "<i>${text}</i>"
        Font.UNDERLINED -> "<u>${text}</u>"
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            } else {
                binding.root.showSnackbar(
                    R.string.geolocation_permission_denied,
                    Snackbar.LENGTH_SHORT
                )
            }
        }
    }

    private fun checkGeoPermission() {
        if (checkSelfPermissionCompat(Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED && checkSelfPermissionCompat(Manifest.permission.ACCESS_COARSE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            getLocation()
        } else {
            requestGeolocationPermission()
        }
    }

    private fun requestGeolocationPermission() {
        if (showRequestPermission(Manifest.permission.ACCESS_FINE_LOCATION) && showRequestPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            binding.root.showSnackbar(
                R.string.geolocation_access_required,
                Snackbar.LENGTH_INDEFINITE, R.string.ok
            ) {
                requestPermissionsCompat(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    PERMISSION_REQUEST_LOCATION
                )
            }

        } else {
            binding.root.showSnackbar(
                R.string.geolocation_permission_not_available,
                Snackbar.LENGTH_SHORT
            )
            requestPermissionsCompat(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), PERMISSION_REQUEST_LOCATION
            )
        }
    }

    private fun getLocation() {
        val cancellationTokenSource = CancellationTokenSource()

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    setLocationText(location)
                } else {
                    fusedLocationClient.getCurrentLocation(
                        LocationRequest.PRIORITY_HIGH_ACCURACY,
                        cancellationTokenSource.token
                    ).addOnSuccessListener { refreshLocation: Location? ->
                        refreshLocation?.let { setLocationText(it) }
                    }
                }
            }
    }
}
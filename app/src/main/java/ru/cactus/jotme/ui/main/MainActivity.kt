package ru.cactus.jotme.ui.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.cactus.jotme.databinding.MainActivityBinding
import ru.cactus.jotme.ui.note.NoteActivity

/**
 * Основной экран приложения
 */
class MainActivity : AppCompatActivity(), MainActivityContract.View {

    private var presenter: MainPresenter? = null
    private lateinit var binding: MainActivityBinding
    private lateinit var mSetting: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        mSetting = getPreferences(Context.MODE_PRIVATE)
        presenter = MainPresenter(this)

        with(binding) {
            llAddNewNote.setOnClickListener {
                presenter?.addNewNoteBtn()
            }
        }
    }

    override fun startNoteActivity() {
        val intentNewNote = Intent(this, NoteActivity::class.java)
        startActivity(intentNewNote)
    }

    companion object {
        private const val NOTE = "Note"
    }

}
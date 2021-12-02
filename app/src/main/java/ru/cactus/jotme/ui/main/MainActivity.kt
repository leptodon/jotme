package ru.cactus.jotme.ui.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.cactus.jotme.R
import ru.cactus.jotme.databinding.MainActivityBinding
import ru.cactus.jotme.ui.note_edit.NoteEditActivity

/**
 * Основной экран приложения
 */
class MainActivity : AppCompatActivity(), MainActivityContract.View {

    private var presenter: MainPresenter? = null
    private var binding: MainActivityBinding? = null
    private lateinit var mSetting: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        mSetting = getPreferences(Context.MODE_PRIVATE)
        presenter = MainPresenter(this)

        binding?.apply {
            llAddNewNote.setOnClickListener {
                presenter?.addNewNoteBtn()
            }
        }

        supportFragmentManager.findFragmentById(R.id.rv_fragment)
    }

    override fun startEditNoteActivity() {
        val intentNewNote = Intent(this, NoteEditActivity::class.java)
        startActivity(intentNewNote)
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}
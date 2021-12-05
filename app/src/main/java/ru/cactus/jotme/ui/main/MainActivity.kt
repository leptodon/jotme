package ru.cactus.jotme.ui.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import ru.cactus.jotme.R
import ru.cactus.jotme.databinding.MainActivityBinding
import ru.cactus.jotme.ui.note_edit.NoteEditActivity
import ru.cactus.jotme.ui.notes.NotesFragment

/**
 * Основной экран приложения
 */
class MainActivity : AppCompatActivity(), MainActivityContract.View {

    private var presenter: MainPresenter? = null
    private var binding: MainActivityBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        presenter = MainPresenter(this)

        initViews()
    }

    private fun initViews(){
        binding?.apply {
            llAddNewNote.setOnClickListener {
                presenter?.addNewNoteBtn()
            }
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.rv_fragment, NotesFragment::class.java, null)
            .commit()
    }

    override fun startEditNoteActivity() {
        val intentNewNote = Intent(this, NoteEditActivity::class.java)
        startActivity(intentNewNote)
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    fun hideNewNoteBtn(isVisible:Boolean){
        binding?.llAddNewNote?.isVisible = isVisible
    }
}
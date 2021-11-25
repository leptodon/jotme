package ru.cactus.jotme.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.main_activity.*
import leakcanary.AppWatcher
import ru.cactus.jotme.R
import ru.cactus.jotme.ui.note.NoteActivity

class MainActivity: AppCompatActivity(), MainActivityContract.View {

    private var presenter: MainPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        presenter = MainPresenter(this)

        ll_add_new_note.setOnClickListener {
            presenter!!.addNewNote()
        }
    }

    override fun startNoteActivity() {
        val intent = Intent(this, NoteActivity::class.java)
        startActivity(intent)
    }

}
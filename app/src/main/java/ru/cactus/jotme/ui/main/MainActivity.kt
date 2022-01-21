package ru.cactus.jotme.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import ru.cactus.jotme.R
import ru.cactus.jotme.databinding.MainActivityBinding
import ru.cactus.jotme.ui.note_edit.NoteEditActivity
import ru.cactus.jotme.ui.notes.NotesFragment
import ru.cactus.jotme.utils.BackupWorker
import ru.cactus.jotme.utils.FRG_MAIN
import java.util.concurrent.TimeUnit

/**
 * Основной экран приложения
 */
class MainActivity : AppCompatActivity(), ButtonController {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)

        initViews()
        initWorker()
    }

    private fun initWorker() {
        WorkManager.getInstance(applicationContext)
            .enqueue(
                PeriodicWorkRequest
                    .Builder(BackupWorker::class.java, 6, TimeUnit.MINUTES)
                    .build()
            )
    }

    private fun initViews() {
        binding.apply {
            llAddNewNote.setOnClickListener {
                startEditNoteActivity()
            }
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.rv_fragment, NotesFragment::class.java, null, FRG_MAIN)
            .commit()
    }

    private fun startEditNoteActivity() {
        val intentNewNote = Intent(this, NoteEditActivity::class.java)
        startActivity(intentNewNote)
    }

    /**
     * Функция скрытия кнопки "New note"
     */
    override fun hideNewNoteBtn(isVisible: Boolean) {
        binding.llAddNewNote.isVisible = isVisible
    }
}
package ru.cactus.jotme.presenter

import android.content.SharedPreferences
import ru.cactus.jotme.Note
import ru.cactus.jotme.contract.ModelInterface
import ru.cactus.jotme.contract.PresenterInterface
import ru.cactus.jotme.contract.ViewInterface
import ru.cactus.jotme.model.MainActivityModel

class MainActivityPresenter(
    _view: ViewInterface,
    private val mSetting: SharedPreferences
) : PresenterInterface {

    private var view: ViewInterface = _view
    private var model: ModelInterface = MainActivityModel()

    init {
        view.initView()
    }

    override fun addNewNote(title: String, body: String) {
        with(mSetting.edit()) {
            putString("NOTE_TITLE", title)
            putString("NOTE_BODY", body)
            apply()
        }
        view.showSaveToast()
    }

    override fun getAllNotes(): List<Note> {
        val title = mSetting.getString("NOTE_TITLE", "New note") ?: "New note"
        val body = mSetting.getString("NOTE_BODY", "Empty body") ?: "Empty body"
        val list = mutableListOf<Note>()
        list.add(Note(1, title, body))
        return list
    }
}
package ru.cactus.jotme.presenter

import android.content.SharedPreferences
import ru.cactus.jotme.Note
import ru.cactus.jotme.contract.ContractInterface
import ru.cactus.jotme.model.MainActivityModel

class MainActivityPresenter(
    _view: ContractInterface.View,
    private val mSetting: SharedPreferences
) : ContractInterface.Presenter {

    private var view: ContractInterface.View = _view
    private var model: ContractInterface.Model = MainActivityModel()

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
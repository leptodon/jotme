package ru.cactus.jotme.ui.note_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class NoteEditViewModelFactory @Inject constructor(
    noteEditViewModelProvider: Provider<NoteEditViewModel>
) : ViewModelProvider.Factory {

    private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
        NoteEditViewModel::class.java to noteEditViewModelProvider
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return providers[modelClass]!!.get() as T
    }
}
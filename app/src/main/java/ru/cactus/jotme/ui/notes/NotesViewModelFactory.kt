package ru.cactus.jotme.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class NotesViewModelFactory @Inject constructor(
    notesViewModelProvider: Provider<NotesViewModel>
) : ViewModelProvider.Factory {

    private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
        NotesViewModel::class.java to notesViewModelProvider
    )

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return providers[modelClass]!!.get() as T
    }
}
package ru.cactus.jotme.ui.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.cactus.jotme.repository.db.DatabaseRepository
import ru.cactus.jotme.repository.db.entity.Note

/**
 * ViewModel класса NotesFragment получает данные из бд и подготавливает для
 * работы с адаптером recyclerView
 * @param databaseRepository репозиторий базы данных
 */
class NotesViewModel(private val databaseRepository: DatabaseRepository) : ViewModel() {

    private val _notesList = MutableLiveData<List<Note>>()

    val notesList: LiveData<List<Note>> = _notesList

    init {
        getNotes()
    }

    private fun getNotes() {
        viewModelScope.launch {
            _notesList.postValue(databaseRepository.getAll())
        }
    }
    
}

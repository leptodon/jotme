package ru.cactus.jotme.ui.note_edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.cactus.jotme.repository.db.DatabaseRepository
import ru.cactus.jotme.repository.entity.Note

/**
 * ViewModel класса NoteEditActivity. Работает с бд
 * @param databaseRepository репозиторий базы данных
 */
class NoteEditViewModel(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private val _showDeleteToast = MutableLiveData<Unit>()

    val showDeleteToast: LiveData<Unit>
        get() = _showDeleteToast

    private val _showSaveToast = MutableLiveData<Unit>()

    val showSaveToast: LiveData<Unit>
        get() = _showSaveToast

    private val _note = MutableLiveData<Note>()

    val note: LiveData<Note>
        get() = _note

    /**
     * Сохранение заметки в бд
     * @param id идентификатор заметки в бд
     * @param title заголовок заметки
     * @param body основной текст заметки
     */
    fun saveNote(id: Int?, title: String, body: String) {
        when {
            title.isNotEmpty() && body.isNotEmpty() -> {
                viewModelScope.launch {
                    databaseRepository.updateInsert(
                        Note(id, title, body)
                    )
                }
            }
        }
        _showSaveToast.postValue(Unit)
    }

    /**
     * Удаление заметки из бд
     * @param id идентификатор заметки в бд
     */
    fun deleteNote(id: Int) {
        viewModelScope.launch {
            databaseRepository.delete(id)
            _showDeleteToast.postValue(Unit)
        }
    }

}
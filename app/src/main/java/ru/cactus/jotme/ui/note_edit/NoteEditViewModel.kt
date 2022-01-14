package ru.cactus.jotme.ui.note_edit

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.cactus.jotme.repository.db.DatabaseRepository
import ru.cactus.jotme.repository.db.entity.Note
import ru.cactus.jotme.repository.network.NetworkRepository
import ru.cactus.jotme.utils.Resource

/**
 * ViewModel класса NoteEditActivity. Работает с бд
 * @param databaseRepository репозиторий базы данных
 */
class NoteEditViewModel(
    private val databaseRepository: DatabaseRepository,
    private val networkRepository: NetworkRepository
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
     * Получение заметки из network
     * @param id идентификатор заметки
     */
    fun getNoteFromNetwork(id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = networkRepository.getNote(id)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

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
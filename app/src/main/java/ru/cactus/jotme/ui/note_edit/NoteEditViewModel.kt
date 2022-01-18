package ru.cactus.jotme.ui.note_edit

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.cactus.jotme.data.repository.db.DatabaseRepository
import ru.cactus.jotme.data.repository.db.entity.DbNote
import ru.cactus.jotme.data.repository.network.NetworkRepository
import ru.cactus.jotme.domain.entity.Note
import ru.cactus.jotme.data.repository.network.NetworkResult
import java.lang.Exception

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

    private val _note = MutableLiveData<DbNote>()

    val dbNote: LiveData<DbNote>
        get() = _note

    val networkResponse: MutableLiveData<NetworkResult<Note>> = MutableLiveData()

    /**
     * Получение заметки из network
     * @param id идентификатор заметки
     */
    fun fetchResponse() {
        viewModelScope.launch {
            try {
                val randomId = (110..120).random()
                val response = networkRepository.getNote(randomId)
                networkResponse.postValue(NetworkResult.Success(response))
            } catch (e: Exception) {
                networkResponse.postValue(NetworkResult.Error(e.message.toString()))
            }
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
                        DbNote(id, title, body)
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
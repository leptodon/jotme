package ru.cactus.jotme.ui.note_edit

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.cactus.jotme.data.repository.db.DatabaseRepository
import ru.cactus.jotme.data.repository.db.DatabaseRepositoryImpl
import ru.cactus.jotme.data.repository.network.NetworkRepository
import ru.cactus.jotme.data.repository.network.NetworkRepositoryImpl
import ru.cactus.jotme.data.repository.network.NetworkResult
import ru.cactus.jotme.domain.entity.Note
import javax.inject.Inject

/**
 * ViewModel класса NoteEditActivity. Работает с бд
 * @param databaseRepository репозиторий базы данных
 * @param networkRepository репозиторий данных из сети
 */
class NoteEditViewModel @Inject constructor(
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

//    @Inject
//    fun testOnceInject(context: Context) {
//        Toast.makeText(context, "Проверка Inject", Toast.LENGTH_LONG).show()
//    }

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

//    class Factory() : ViewModelProvider.Factory {
//        @Suppress("UNCHECKED_CAST")
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            return NoteEditViewModel() as T
//        }
//    }
}



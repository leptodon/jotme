package ru.cactus.jotme.ui.swiper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.cactus.jotme.data.repository.db.DatabaseRepository
import ru.cactus.jotme.domain.entity.Note

/**
 * ViewModel класса PageSwiperFragment получает данные из бд и подготавливает для
 * работы с адаптером viewPager2
 * @param databaseRepository репозиторий базы данных
 */
class PageSwiperViewModel(private val databaseRepository: DatabaseRepository) : ViewModel() {

    private val _notesList = MutableLiveData<List<Note>>()

    val notesList: LiveData<List<Note>>
        get() {
            getNotesList()
            return _notesList
        }

    init {
        getNotesList()
    }

    private fun getNotesList() {
        viewModelScope.launch {
            _notesList.postValue(
                databaseRepository.getAll()
            )
        }
    }
}
package ru.cactus.jotme.ui.swiper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class PageSwiperViewModelFactory @Inject constructor(
    pageSwiperViewModelProvider: Provider<PageSwiperViewModel>
) : ViewModelProvider.Factory {

    private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
        PageSwiperViewModel::class.java to pageSwiperViewModelProvider
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return providers[modelClass]!!.get() as T
    }
}
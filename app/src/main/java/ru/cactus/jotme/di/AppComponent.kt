package ru.cactus.jotme.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.cactus.jotme.ui.note_edit.NoteEditActivity
import ru.cactus.jotme.ui.note_edit.NoteEditViewModelFactory
import ru.cactus.jotme.ui.notes.NotesViewModel
import ru.cactus.jotme.ui.notes.NotesViewModelFactory
import ru.cactus.jotme.ui.swiper.PageSwiperViewModelFactory
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(noteEditActivity: NoteEditActivity)
    fun inject(notesViewModel: NotesViewModel)
    fun noteEditViewModelFactory(): NoteEditViewModelFactory
    fun pageSwiperViewModelFactory(): PageSwiperViewModelFactory
    fun notesViewModelFactory(): NotesViewModelFactory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }
}
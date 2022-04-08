package ru.cactus.jotme.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.cactus.jotme.ui.note_edit.NoteEditViewModelFactory
import ru.cactus.jotme.ui.notes.NotesViewModelFactory
import ru.cactus.jotme.ui.swiper.PageSwiperViewModelFactory
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent : FeatureDeps {

    override fun context(): Context

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Context): Builder
        fun build(): AppComponent
    }
}

@Singleton
@Component(modules = [FeatureModule::class], dependencies = [FeatureDeps::class])
interface FeatureComponent {
    fun noteEditViewModelFactory(): NoteEditViewModelFactory
    fun pageSwiperViewModelFactory(): PageSwiperViewModelFactory
    fun notesViewModelFactory(): NotesViewModelFactory

    @Component.Builder
    interface Builder {
        fun deps(deps: FeatureDeps): Builder
        fun build(): FeatureComponent
    }
}

interface FeatureDeps {
    fun context(): Context
}

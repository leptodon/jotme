package ru.cactus.jotme.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.cactus.jotme.data.repository.AppDatabase
import ru.cactus.jotme.data.repository.db.DatabaseRepository
import ru.cactus.jotme.data.repository.db.DatabaseRepositoryImpl
import ru.cactus.jotme.data.repository.network.ApiService
import ru.cactus.jotme.data.repository.network.NetworkRepository
import ru.cactus.jotme.data.repository.network.NetworkRepositoryImpl
import javax.inject.Singleton

private const val BASE_URL = "https://stoplight.io/mocks/leptodon/noteapp/781080/"

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideNetworkRepository(apiService: ApiService): NetworkRepository {
        return NetworkRepositoryImpl(apiService = apiService)
    }

    @Singleton
    @Provides
    fun provideRetrofit(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideDatabaseRepository(db: AppDatabase): DatabaseRepository {
        return DatabaseRepositoryImpl(db = db)
    }

    @Singleton
    @Provides
    fun providesRoomDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "notes_db.db"
        ).build()
    }
}
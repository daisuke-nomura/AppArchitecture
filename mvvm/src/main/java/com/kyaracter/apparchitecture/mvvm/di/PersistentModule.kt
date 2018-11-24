package com.kyaracter.apparchitecture.mvvm.di

import android.content.Context
import androidx.room.Room
import com.kyaracter.apparchitecture.mvvm.data.local.db.AppDatabase
import com.kyaracter.apparchitecture.mvvm.data.local.db.dao.RepoDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
internal class PersistentModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app.db").build()
    }

    @Provides
    @Singleton
    fun provideRepoDao(appDatabase: AppDatabase): RepoDao {
        return appDatabase.repoDao()
    }
}

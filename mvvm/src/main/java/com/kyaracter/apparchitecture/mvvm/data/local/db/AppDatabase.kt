package com.kyaracter.apparchitecture.mvvm.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kyaracter.apparchitecture.mvvm.data.local.db.dao.RepoDao
import com.kyaracter.apparchitecture.mvvm.data.local.db.entity.Repo


@Database(entities = [Repo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repoDao(): RepoDao
}
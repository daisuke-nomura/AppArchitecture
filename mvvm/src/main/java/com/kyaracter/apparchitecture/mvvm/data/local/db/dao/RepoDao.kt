package com.kyaracter.apparchitecture.mvvm.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kyaracter.apparchitecture.mvvm.data.local.db.entity.Repo
import io.reactivex.Flowable


@Dao
interface RepoDao {

    @Query("SELECT * FROM repo WHERE owner = :owner")
    fun get(owner: String): Flowable<List<Repo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repo: List<Repo>)
}
package com.kyaracter.apparchitecture.mvvm.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Repo {

    @PrimaryKey
    var id: Long = 0

    @ColumnInfo
    lateinit var name: String

    @ColumnInfo
    lateinit var owner: String

    @ColumnInfo
    var description: String? = null

    @ColumnInfo
    lateinit var url: String
}

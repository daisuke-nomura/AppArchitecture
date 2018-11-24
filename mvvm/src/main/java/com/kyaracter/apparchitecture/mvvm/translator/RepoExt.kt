package com.kyaracter.apparchitecture.mvvm.translator

import com.kyaracter.apparchitecture.mvvm.data.local.db.entity.Repo
import com.kyaracter.apparchitecture.mvvm.data.remote.entity.RemoteRepo
import com.kyaracter.apparchitecture.mvvm.presentation.main.entity.RepoItem


fun List<RemoteRepo>.toRepo(): List<Repo> {
    return this
        .map {
            Repo().apply {
                this.id = it.id
                this.name = it.name
                this.owner = it.owner.login
                this.description = it.description
                this.url = it.url
            }
        }
}

fun List<Repo>.toRepoItem(): List<RepoItem> {
    return this
        .map {
            RepoItem(it.name, it.description ?: "", it.url)
        }
}
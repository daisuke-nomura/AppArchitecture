package com.kyaracter.apparchitecture.mvvm.data.repository

import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import com.kyaracter.apparchitecture.mvvm.data.local.db.dao.RepoDao
import com.kyaracter.apparchitecture.mvvm.data.local.db.entity.Repo
import com.kyaracter.apparchitecture.mvvm.data.remote.entity.RemoteRepo
import com.kyaracter.apparchitecture.mvvm.data.remote.service.UserService
import com.kyaracter.apparchitecture.mvvm.translator.toRepo
import io.reactivex.Completable
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class RepoRepository @Inject constructor(
    private val repoDao: RepoDao,
    private val userService: UserService
) {

    val relay: Relay<List<Repo>> = PublishRelay.create()

    fun fetch(owner: String): Single<List<RemoteRepo>> {
        return userService
            .fetch(owner)
            .doOnSuccess {
                val repo = it.toRepo()
                repoDao.insert(repo)
            }
            .doOnError {
                Timber.e(it)
            }
    }

    fun get(owner: String): Completable {
        return repoDao
            .get(owner)
            .doOnNext {
                relay.accept(it)
            }
            .ignoreElements()
    }
}
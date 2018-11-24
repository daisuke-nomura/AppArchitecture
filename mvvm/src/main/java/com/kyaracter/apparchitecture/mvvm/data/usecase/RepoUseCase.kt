package com.kyaracter.apparchitecture.mvvm.data.usecase

import com.kyaracter.apparchitecture.mvvm.data.repository.RepoRepository
import com.kyaracter.apparchitecture.mvvm.presentation.main.entity.RepoItem
import com.kyaracter.apparchitecture.mvvm.translator.toRepoItem
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class RepoUseCase @Inject constructor(
    private val repoRepository: RepoRepository
) {

    val relay: Observable<List<RepoItem>> = repoRepository.relay
        .map { it.toRepoItem() }

    fun fetch(owner: String): Single<Int> {
        return repoRepository
            .fetch(owner)
            .map { it.count() }
    }

    fun get(owner: String): Completable {
        return repoRepository
            .get(owner)
    }
}
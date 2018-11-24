package com.kyaracter.apparchitecture.mvvm.presentation.main

import androidx.lifecycle.ViewModel
import com.kyaracter.apparchitecture.mvvm.data.usecase.RepoUseCase
import com.kyaracter.apparchitecture.mvvm.presentation.main.entity.RepoItem
import io.reactivex.Completable
import io.reactivex.Observable
import jp.keita.kagurazaka.rxproperty.RxProperty
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repoUseCase: RepoUseCase
) : ViewModel() {

    val progress = RxProperty(false)

    val nodata = RxProperty(false)

    val error = RxProperty(false)

    val list: Observable<List<RepoItem>> = repoUseCase.relay

    val name = RxProperty("daisuke-nomura")

    fun fetch(): Completable {
        return repoUseCase
            .fetch(name.get())
            .doOnSubscribe {
                progress.set(true)
                nodata.set(false)
                error.set(false)
            }
            .doOnSuccess {
                if (it == 0) {
                    nodata.set(true)
                }
            }
            .doOnError {
                error.set(true)
            }
            .doFinally {
                progress.set(false)
            }
            .ignoreElement()
    }

    fun get(): Completable {
        return repoUseCase
            .get(name.get())
    }
}

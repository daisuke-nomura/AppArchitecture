package com.kyaracter.apparchitecture.mvvm.di


import android.os.Looper
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
internal class SchedulerModule {

    @Provides
    @Singleton
    @Named("ioScheduler")
    fun provideIoScheduler(): Scheduler {
        return Schedulers.io()
    }

    @Provides
    @Singleton
    @Named("uiScheduler")
    fun provideUiScheduler(): Scheduler {
        return AndroidSchedulers.from(Looper.getMainLooper(), true)
    }
}

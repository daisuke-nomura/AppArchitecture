package com.kyaracter.apparchitecture.mvvm.di

import androidx.lifecycle.ViewModelProvider
import com.kyaracter.apparchitecture.mvvm.presentation.main.MainActivity
import com.kyaracter.apparchitecture.mvvm.presentation.main.di.MainModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun mainActivity(): MainActivity
}
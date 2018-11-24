package com.kyaracter.apparchitecture.mvvm.presentation.main.di

import androidx.lifecycle.ViewModel
import com.kyaracter.apparchitecture.mvvm.di.annotation.ViewModelKey
import com.kyaracter.apparchitecture.mvvm.presentation.main.MainFragment
import com.kyaracter.apparchitecture.mvvm.presentation.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @ContributesAndroidInjector
    abstract fun mainFragment(): MainFragment
}
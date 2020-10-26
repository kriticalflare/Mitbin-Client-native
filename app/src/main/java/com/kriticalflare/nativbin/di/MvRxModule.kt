package com.kriticalflare.nativbin.di

import com.kriticalflare.nativbin.history.HistoryViewModel
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.multibindings.IntoMap

@AssistedModule
@InstallIn(ApplicationComponent::class)
@Module
interface MvRxModule {

    @Binds
    @IntoMap
    @ViewModelKey(HistoryViewModel::class)
    fun historyViewModelFactory(factory: HistoryViewModel.Factory): AssistedViewModelFactory<*, *>
}
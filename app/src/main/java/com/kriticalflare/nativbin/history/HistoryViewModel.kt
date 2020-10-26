package com.kriticalflare.nativbin.history

import android.util.Log
import com.airbnb.mvrx.*
import com.kriticalflare.nativbin.di.AssistedViewModelFactory
import com.kriticalflare.nativbin.di.BaseViewModel
import com.kriticalflare.nativbin.di.DaggerMvRxViewModelFactory
import com.kriticalflare.nativbin.history.data.HistoryRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryViewModel @AssistedInject constructor(
    @Assisted initialState: HistoryViewState,
    private val historyRepository: HistoryRepository
) : BaseViewModel<HistoryViewState>(initialState = initialState) {

    init {
        fetchHistory()
    }

    private fun fetchHistory() = withState {
        viewModelScope.launch {
            historyRepository.getViewedPastes().execute {
                copy(historyList = it)
            }
            Log.d("vm4", "$it")
        }
    }

    @AssistedInject.Factory
    interface Factory : AssistedViewModelFactory<HistoryViewModel, HistoryViewState> {
        override fun create(initialState: HistoryViewState): HistoryViewModel
    }

    companion object : DaggerMvRxViewModelFactory<HistoryViewModel, HistoryViewState>(HistoryViewModel::class.java)
}
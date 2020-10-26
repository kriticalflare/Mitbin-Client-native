package com.kriticalflare.nativbin.history

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized
import com.kriticalflare.nativbin.history.data.ViewedPaste

data class HistoryViewState(val historyList: Async<List<ViewedPaste>> = Uninitialized): MavericksState
package com.kriticalflare.nativbin.di

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel

abstract class BaseViewModel<S : MavericksState>(initialState: S) : MavericksViewModel<S>(initialState)
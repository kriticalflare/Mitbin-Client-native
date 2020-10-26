package com.kriticalflare.nativbin.viewPaste

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.kriticalflare.bin_wrapper.BinWrapper
import com.kriticalflare.bin_wrapper.remote.model.Paste
import com.kriticalflare.nativbin.history.data.HistoryRepository
import com.kriticalflare.nativbin.history.data.ViewedPaste
import com.pddstudio.highlightjs.models.Language
import kotlinx.coroutines.launch

class ViewPasteViewModel @ViewModelInject constructor(
    private val historyRepository: HistoryRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val binWrapper = BinWrapper()
    private var _pastes = MutableLiveData<PasteResult>()
    val pastes: LiveData<PasteResult> = _pastes

    fun viewPaste(name: String) {
        viewModelScope.launch {
            try {
                _pastes.value = PasteResult.Loading
                val pastes = binWrapper.getPaste(name)
                if (pastes.isEmpty()) {
                    _pastes.value = PasteResult.Error("Paste name is incorrect or it has expired")
                } else if (pastes.isNotEmpty()) {
                    _pastes.value = PasteResult.Success(pastes[0]).also {
                        historyRepository.insertViewedPaste(
                            ViewedPaste(
                                it.paste.name,
                                it.paste.content,
                                it.paste.language,
                                it.paste.createdAt,
                                it.paste.expiresAt
                            )
                        )
                    }
                } else {
                    _pastes.value = PasteResult.Error("Unknown Error")
                }
            } catch (t: Throwable) {
                _pastes.value = PasteResult.Error("${t.message}")
            }
        }
    }

    fun getContentLanguage(paste: Paste): Language? {
        when (paste.language) {
            "clike" -> {
                return Language.C
            }
            "python" -> {
                return Language.PYTHON
            }
            "json" -> {
                return Language.JSON
            }
            "Plain Text" -> {
                return Language.JSON
            }
            else -> {
                return Language.JSON
            }
        }
    }
}

sealed class PasteResult {
    object Loading : PasteResult()
    class Error(val error: String) : PasteResult()
    class Success(val paste: Paste) : PasteResult()
}
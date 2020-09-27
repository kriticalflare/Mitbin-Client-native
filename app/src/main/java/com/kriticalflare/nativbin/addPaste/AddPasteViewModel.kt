package com.kriticalflare.nativbin.addPaste

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kriticalflare.bin_wrapper.BinWrapper
import com.kriticalflare.bin_wrapper.data.PasteResponse
import com.kriticalflare.bin_wrapper.remote.model.Paste
import com.kriticalflare.bin_wrapper.remote.model.UploadPaste
import com.kriticalflare.nativbin.utils.Event
import kotlinx.coroutines.launch

class AddPasteViewModel: ViewModel(){
    private val binWrapper = BinWrapper()

    private val _uploadStatus = MutableLiveData<Event<PasteResponse>>()

    val uploadStatus : LiveData<Event<PasteResponse>>
        get() = _uploadStatus


    fun uploadPaste(paste: UploadPaste){
        println(paste)
        viewModelScope.launch {
            _uploadStatus.value = Event(binWrapper.addPaste(paste))
            println(_uploadStatus.value)
        }
    }
}
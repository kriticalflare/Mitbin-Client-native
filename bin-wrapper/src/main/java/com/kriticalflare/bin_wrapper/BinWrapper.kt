package com.kriticalflare.bin_wrapper

import com.kriticalflare.bin_wrapper.data.PasteRepository
import com.kriticalflare.bin_wrapper.remote.MitBinServiceBuilder
import com.kriticalflare.bin_wrapper.remote.model.Paste

class BinWrapper {
    private val pasteRepository by lazy {
        PasteRepository(MitBinServiceBuilder.retrofit)
    }

    suspend fun getPaste(name: String): List<Paste> {
        return pasteRepository.getPaste(name)
    }
}
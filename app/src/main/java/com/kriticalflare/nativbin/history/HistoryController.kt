package com.kriticalflare.nativbin.history

import android.widget.Toast
import com.airbnb.epoxy.EpoxyController
import com.kriticalflare.nativbin.history.data.ViewedPaste

class HistoryController : EpoxyController() {

    var allViewedPaste: List<ViewedPaste> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        history {
            id("1")
            name("Test")
        }
        allViewedPaste.forEach {
            history {
                id(it.name)
                name(it.name)
                language(it.language)
                cardOnClick { model, parentView, clickedView, position ->
                    Toast.makeText(parentView.cardView.context, "$position", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
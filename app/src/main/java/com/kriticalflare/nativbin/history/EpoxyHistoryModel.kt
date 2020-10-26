package com.kriticalflare.nativbin.history

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.google.android.material.card.MaterialCardView
import com.kriticalflare.nativbin.R
import kotlinx.android.synthetic.main.item_history.view.*

@EpoxyModelClass(layout = R.layout.item_history)
abstract class HistoryModel : EpoxyModelWithHolder<HistoryModel.HistoryHolder>() {

    @EpoxyAttribute
    var name: CharSequence = ""

    @EpoxyAttribute
    var language: CharSequence = ""

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var cardOnClick: View.OnClickListener? = null

    override fun bind(holder: HistoryHolder) {
        holder.itemName.text = name
        holder.itemLanguage.text = language
    }

    override fun unbind(holder: HistoryHolder) {
        holder.cardView.setOnClickListener(null)
    }

    inner class HistoryHolder : EpoxyHolder() {
        lateinit var itemName: TextView
        lateinit var cardView: MaterialCardView
        lateinit var itemLanguage: TextView

        override fun bindView(itemView: View) {
            itemName = itemView.paste_name
            itemLanguage = itemView.paste_language
            cardView = itemView.history_card
            cardView.setOnClickListener(cardOnClick)
        }

    }
}
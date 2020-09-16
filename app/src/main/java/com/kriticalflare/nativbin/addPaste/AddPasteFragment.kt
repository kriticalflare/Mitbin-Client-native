package com.kriticalflare.nativbin.addPaste

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kriticalflare.nativbin.R

class AddPasteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_paste, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddPasteFragment()
    }
}
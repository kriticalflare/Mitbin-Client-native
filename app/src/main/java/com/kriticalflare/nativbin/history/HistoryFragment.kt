package com.kriticalflare.nativbin.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.airbnb.epoxy.EpoxyController
import com.airbnb.mvrx.*
import com.kriticalflare.nativbin.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment(), MavericksView {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val historyViewModel: HistoryViewModel by fragmentViewModel()
    private val epoxyController by lazy {
        HistoryController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        binding.epoxyRecycler.setController(epoxyController)
        return binding.root
    }

    override fun invalidate() = withState(historyViewModel) { state ->
        when (state.historyList) {
            is Success -> {
                epoxyController.allViewedPaste = state.historyList.invoke()
                epoxyController.requestModelBuild()
            }
            else -> {

            }
        }
    }
}
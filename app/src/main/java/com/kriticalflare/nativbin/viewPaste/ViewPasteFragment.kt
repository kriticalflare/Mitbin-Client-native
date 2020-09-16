package com.kriticalflare.nativbin.viewPaste

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.kriticalflare.nativbin.R
import com.kriticalflare.nativbin.databinding.FragmentViewPasteBinding


/**
 * A simple [Fragment] subclass.
 * Use the [ViewPasteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewPasteFragment : Fragment() {

    private var _binding: FragmentViewPasteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentViewPasteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPasteViewModel by viewModels<ViewPasteViewModel>()

        arguments?.getString("name")?.let {
            binding.pasteText.setText(it)
            viewPasteViewModel.viewPaste(it)
        }
        viewPasteViewModel.pastes.observe(viewLifecycleOwner, Observer { pasteResult ->
            when (pasteResult) {
                is PasteResult.Loading -> {
                    binding.viewPasteResultText.visibility = View.GONE
                    binding.spinKit.visibility = View.VISIBLE
                }
                is PasteResult.Error -> {
                    binding.viewPasteResultText.text = pasteResult.error
                    binding.codeView.visibility = View.GONE
                    binding.viewPasteResultText.visibility = View.VISIBLE
                    binding.spinKit.visibility = View.GONE
                }
                is PasteResult.Success -> {
                    binding.codeView.apply {
                        setShowLineNumbers(true)
                        setZoomSupportEnabled(true)
                        visibility = View.VISIBLE
                        setSource(pasteResult.paste.content)
                        highlightLanguage = viewPasteViewModel.getContentLanguage(pasteResult.paste)
                    }
                    binding.viewPasteResultText.visibility = View.GONE
                    binding.spinKit.visibility = View.GONE
                }
                else -> {
                    // initial state
                    binding.viewPasteResultText.text = "View paste"
                    binding.viewPasteResultText.visibility = View.VISIBLE
                }
            }
        })

        binding.pasteSearchBtn.setOnClickListener {
            viewPasteViewModel.viewPaste(binding.pasteText.text.toString())
        }

        binding.addPasteFab.setOnClickListener {
            findNavController().navigate(R.id.action_viewPasteFragment_to_addPasteFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ViewPasteFragment()
    }
}
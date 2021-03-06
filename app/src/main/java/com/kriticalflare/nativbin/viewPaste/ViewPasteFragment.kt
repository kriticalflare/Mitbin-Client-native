package com.kriticalflare.nativbin.viewPaste

import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialElevationScale
import com.kriticalflare.nativbin.R
import com.kriticalflare.nativbin.databinding.FragmentViewPasteBinding
import com.pddstudio.highlightjs.models.Theme;
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [ViewPasteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ViewPasteFragment : Fragment() {

    private var _binding: FragmentViewPasteBinding? = null
    private val binding get() = _binding!!
    private val viewPasteViewModel:ViewPasteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentViewPasteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.codeView.apply {
            theme = if (isDarkMode()) Theme.DARK else Theme.GOOGLECODE
        }

        binding.mvrxFab.setOnClickListener {
            findNavController().navigate(R.id.action_viewPasteFragment_to_historyFragment)
        }
        arguments?.getString("name")?.let {
            binding.pasteText.setText(it)
            viewPasteViewModel.viewPaste(it)
        }
        viewPasteViewModel.pastes.observe(viewLifecycleOwner, Observer { pasteResult ->
            when (pasteResult) {
                is PasteResult.Loading -> {
                    binding.codeView.visibility = View.GONE
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
                        highlightLanguage = viewPasteViewModel.getContentLanguage(pasteResult.paste)
                        setSource(pasteResult.paste.content)
                        visibility = View.VISIBLE
                        setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.codeViewBackground))
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
            val extras = FragmentNavigatorExtras(binding.addPasteFab to "create_paste_transform")
            findNavController().navigate(R.id.action_viewPasteFragment_to_addPasteFragment, null, null, extras)
        }
    }

    private fun isDarkMode(): Boolean {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        } else {
            resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK === Configuration.UI_MODE_NIGHT_YES
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
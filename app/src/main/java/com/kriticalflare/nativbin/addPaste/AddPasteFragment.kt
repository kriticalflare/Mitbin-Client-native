package com.kriticalflare.nativbin.addPaste

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import com.kriticalflare.bin_wrapper.data.PasteResponse
import com.kriticalflare.bin_wrapper.remote.model.UploadPaste
import com.kriticalflare.nativbin.R
import com.kriticalflare.nativbin.databinding.FragmentAddPasteBinding
import com.kriticalflare.nativbin.utils.TimeInMinutes
import com.kriticalflare.nativbin.utils.formatLanguageApi
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem


class AddPasteFragment : Fragment() {

    private var _binding: FragmentAddPasteBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            scrimColor = Color.TRANSPARENT
            duration = 400
            setPathMotion(MaterialArcMotion())
            interpolator = FastOutLinearInInterpolator()
            fadeMode = MaterialContainerTransform.FADE_MODE_OUT
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddPasteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel by viewModels<AddPasteViewModel>()

        val languageMenuList = listOf(
            PowerMenuItem("Plain Text", true),
            PowerMenuItem("Python"),
            PowerMenuItem("Json"),
            PowerMenuItem("C Like")
        )

        val timeMenuList = listOf(
            PowerMenuItem("One day", true),
            PowerMenuItem("One week"),
            PowerMenuItem("One month")
        )

        val languageMenu = setupPowerMenu(languageMenuList,viewLifecycleOwner)
        val timeMenu = setupPowerMenu(timeMenuList, viewLifecycleOwner)


        binding.languageImg.setOnClickListener {
            languageMenu.showAsDropDown(binding.languageImg)
        }

        binding.timeImg.setOnClickListener {
            timeMenu.showAsDropDown(binding.timeImg)
        }

        binding.uploadFab.setOnClickListener {
            if(isValidPasteName(binding)){
                viewModel.uploadPaste(UploadPaste(
                    name = binding.pasteUrlEdit.text.toString(),
                    content = binding.pasteCodeLayout.text,
                    language = languageMenuList[languageMenu.selectedPosition].title.toString().formatLanguageApi(),
                    expiresInMinutes = TimeInMinutes(timeMenuList[timeMenu.selectedPosition].title.toString())
                ))
            }
        }

        viewModel.uploadStatus.observe(viewLifecycleOwner, Observer {
            when(val event = it.getContentIfNotHandled()){
                is PasteResponse.Success -> {
                    findNavController().popBackStack()
                }
                is PasteResponse.Failure -> {
                    Snackbar.make(binding.root, event.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun setupPowerMenu(list: List<PowerMenuItem>, lifecycleOwner: LifecycleOwner): PowerMenu {
        val powerMenu = PowerMenu.Builder(requireContext())
            .addItemList(list)
            .setMenuRadius(10f)
            .setMenuShadow(10f)
            .setLifecycleOwner(lifecycleOwner)
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.popupText))
            .setTextGravity(Gravity.CENTER)
            .setTextTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD))
            .setSelectedTextColor(Color.WHITE)
            .setMenuColor(ContextCompat.getColor(requireContext(), R.color.popupBackground))
            .setSelectedMenuColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
            .setSelected(0)
            .build()

        powerMenu.setOnMenuItemClickListener { position, item ->
            powerMenu.selectedPosition = position
            Toast.makeText(requireContext(), item.title, Toast.LENGTH_SHORT).show()
        }
        return powerMenu
    }

    private fun isValidPasteName(binding: FragmentAddPasteBinding): Boolean {
        return if(binding.pasteUrlEdit.text.toString().isEmpty()){
            binding.editLayout.isErrorEnabled = true
            binding.editLayout.error = "Paste name cannot be empty"
            false
        } else {
            if(binding.editLayout.isErrorEnabled){
                binding.editLayout.error = null
            }
            true
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddPasteFragment()
    }
}
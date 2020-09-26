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
import androidx.lifecycle.LifecycleOwner
import com.kriticalflare.nativbin.R
import com.kriticalflare.nativbin.databinding.FragmentAddPasteBinding
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem


class AddPasteFragment : Fragment() {

    private var _binding: FragmentAddPasteBinding? = null
    private val binding get() = _binding!!

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
            .build()

        powerMenu.setOnMenuItemClickListener { position, item ->
            powerMenu.selectedPosition = position
            Toast.makeText(requireContext(), item.title, Toast.LENGTH_SHORT).show()
        }
        return powerMenu
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
package com.kriticalflare.nativbin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.kriticalflare.nativbin.databinding.ActivityMainBinding
import com.kriticalflare.nativbin.viewPaste.PasteResult
import com.kriticalflare.nativbin.viewPaste.ViewPasteViewModel
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewPasteViewModel by viewModels<ViewPasteViewModel>()
        viewPasteViewModel.pastes.observe(this, Observer { pasteResult ->
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
    }
}
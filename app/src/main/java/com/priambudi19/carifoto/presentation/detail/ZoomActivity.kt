package com.priambudi19.carifoto.presentation.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import coil.load
import com.priambudi19.carifoto.R
import com.priambudi19.carifoto.databinding.ActivityZoomBinding

class ZoomActivity : AppCompatActivity() {
    private val viewModel: ZoomViewModel by viewModels()
    private lateinit var binding: ActivityZoomBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getStringExtra("img_url").let { url ->
            viewModel.setUrl(url)
        }
        binding = ActivityZoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.url.observe(this,::initUi)
    }

    private fun initUi(url: String?) {
        binding.apply {
            photoView.load(url)
            zoomToolbar.btnBack.setOnClickListener { finishAndRemoveTask() }
            zoomToolbar.txtToolbarTitle.text = getString(R.string.zoom_preview)
            zoomToolbar.btnBack.setImageDrawable(ContextCompat.getDrawable(this@ZoomActivity, R.drawable.ic_close_fullscreen))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.zoomToolbar.btnBack.setImageDrawable(ContextCompat.getDrawable(this@ZoomActivity, R.drawable.ic_close_fullscreen))
    }
}
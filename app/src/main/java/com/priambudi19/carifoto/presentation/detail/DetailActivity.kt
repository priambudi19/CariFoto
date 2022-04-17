package com.priambudi19.carifoto.presentation.detail

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.net.toUri
import androidx.work.WorkInfo
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.snackbar.Snackbar
import com.priambudi19.carifoto.R
import com.priambudi19.carifoto.data.model.Photo
import com.priambudi19.carifoto.databinding.ActivityDetailBinding
import com.priambudi19.carifoto.download.FotoDownloadManager
import com.priambudi19.carifoto.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.getStringExtra("id")?.let {
            viewModel.getDetail(it)
            viewModel.detail.observe(this, ::initUi)
            viewModel.checkIsFavorite().observe(this, ::isFavorited)
        }
    }


    private fun initUi(resource: Resource<Photo>) {
        when (resource) {
            is Resource.Loading -> {}
            is Resource.Success -> {
                binding.apply {
                    imgListItem.load(resource.data?.urls?.regular){
                        error(R.drawable.ic_broken)
                        placeholder(R.drawable.ic_foto_loading)
                    }
                    imgUser.load(resource.data?.user?.profileImage?.medium) {
                        error(R.drawable.ic_broken)
                        transformations(CircleCropTransformation())
                    }
                    txtDescription.text = if (!resource.data?.description.isNullOrBlank()) {
                        resource.data?.description
                    } else {
                        resource.data?.altDescription ?: "No Description"
                    }
                    txtLocation.text = resource.data?.user?.location
                    txtName.text = resource.data?.user?.name
                    btnDownload.setOnClickListener { download(resource.data!!) }
                    btnAddFavorite.setOnClickListener {
                        addOrDelete(resource.data!!)
                    }
                    btnFullscreen.setOnClickListener {
                        fullScreen(resource.data?.urls?.regular)
                    }
                    btnVisit.setOnClickListener { visitSourcePage(resource.data?.links?.html) }
                    btnShare.setOnClickListener { shareUrl(resource.data?.links?.html) }
                    toolbarDetail.txtToolbarTitle.text = resource.data?.user?.name
                    toolbarDetail.btnBack.setOnClickListener { finishAndRemoveTask() }
                }
            }
            is Resource.Error -> {}
            else -> Unit
        }
    }

    private fun download(photo: Photo) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    101
                )
            }
        }
        viewModel.download(photo, this).observe(this) {
            val workInfo = it
            when (it.state) {
                WorkInfo.State.RUNNING -> {
                    Toast.makeText(this, "Downloading...", Toast.LENGTH_SHORT).show()
                }
                WorkInfo.State.SUCCEEDED -> {
                    Snackbar.make(binding.root, "Download Finsished", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Open") {
                            val i = Intent(Intent.ACTION_VIEW).apply {
                                val uri =
                                    workInfo.outputData.getString(FotoDownloadManager.FileParams.KEY_FILE_URI)
                                        ?.toUri()
                                Log.d("TAG download", "data: " + uri.toString())
                                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                setDataAndType(uri, "image/*")
                            }
                            startActivity(Intent.createChooser(i, "Open with"))
                        }
                        .show()

                }
                WorkInfo.State.FAILED -> {
                    Toast.makeText(
                        this,
                        "Download Error: Check your connection or storage space",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {}
            }
        }
    }

    private fun addOrDelete(data: Photo) {
        if (viewModel.isFavorite) {
            viewModel.deleteFromFavorite(data)
            Toast.makeText(this, "Deleted to favorite", Toast.LENGTH_SHORT).show()
            return
        }
        viewModel.addToFavorite(data)
        Toast.makeText(this, "Inserted to favorite", Toast.LENGTH_SHORT).show()
    }


    private fun isFavorited(fav: List<Photo>) {
        Log.d("TAG", "isFavorited: ${fav.size}")
        with(binding) {
            if (fav.isNotEmpty()) {
                viewModel.isFavorite = true
                btnAddFavorite.setBackgroundColor(
                    getColor(
                        this@DetailActivity,
                        R.color.delete_favorite
                    )
                )
                btnAddFavorite.icon =
                    getDrawable(this@DetailActivity, R.drawable.ic_star)

            } else {
                viewModel.isFavorite = false
                btnAddFavorite.setBackgroundColor(
                    getColor(
                        this@DetailActivity,
                        R.color.add_favorite
                    )
                )
                btnAddFavorite.icon =
                    getDrawable(this@DetailActivity, R.drawable.ic_star_border)
            }
        }
    }


    private fun fullScreen(url: String?) {
        val i = Intent(this, ZoomActivity::class.java).apply {
            putExtra("img_url", url)
        }
        startActivity(i)
    }

    private fun shareUrl(url: String?) {
        val i = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, url)
        }
        val share = Intent.createChooser(i, "Share..")
        startActivity(share)
    }

    private fun visitSourcePage(html: String?) {
        val i = Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(html)
        }
        startActivity(i)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}
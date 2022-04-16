package com.priambudi19.carifoto.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.priambudi19.carifoto.R
import com.priambudi19.carifoto.databinding.FragmentFavoriteBinding
import com.priambudi19.carifoto.presentation.adapter.PhotoAdapter
import com.priambudi19.carifoto.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private val viewModel: FavoriteViewModel by viewModels()
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val mAdapter: PhotoAdapter by lazy { PhotoAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setData()
        initUi()
        mAdapter.onClick = {
            toDetailPhoto(it.id)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.setData()
    }

    private fun toDetailPhoto(id: String) {
        findNavController().navigate(
            R.id.action_favoriteFragment_to_detailActivity,
            bundleOf("id" to id)
        )
    }

    private fun initUi() {
        binding.apply {
            favoriteToolbar.txtToolbarTitle.text = getString(R.string.list_favorite)
            favoriteToolbar.btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            viewModel.getListFavorite.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> {
                        rvFavorite.visibility = View.INVISIBLE
                        loadingResult.visibility = View.VISIBLE
                        txtMessage.visibility = View.VISIBLE
                        errorResult.visibility = View.GONE
                        emptyResult.visibility = View.GONE
                        txtMessage.text = getString(R.string.loading)
                    }
                    is Resource.Success -> {
                        rvFavorite.visibility = View.VISIBLE
                        rvFavorite.adapter = mAdapter
                        mAdapter.setData(it.data!!)
                        loadingResult.visibility = View.GONE
                        txtMessage.visibility = View.GONE
                        errorResult.visibility = View.GONE
                        emptyResult.visibility = View.GONE
                    }
                    is Resource.Error -> {
                        rvFavorite.visibility = View.INVISIBLE
                        loadingResult.visibility = View.GONE
                        txtMessage.visibility = View.VISIBLE
                        txtMessage.text = getString(R.string.error_connection)
                        errorResult.visibility = View.VISIBLE
                        emptyResult.visibility = View.GONE
                    }
                    is Resource.Empty -> {
                        rvFavorite.visibility = View.INVISIBLE
                        loadingResult.visibility = View.GONE
                        txtMessage.visibility = View.VISIBLE
                        txtMessage.text = getString(R.string.no_data)
                        errorResult.visibility = View.GONE
                        emptyResult.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
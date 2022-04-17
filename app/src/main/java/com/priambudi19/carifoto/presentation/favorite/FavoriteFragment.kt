package com.priambudi19.carifoto.presentation.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.priambudi19.carifoto.R
import com.priambudi19.carifoto.databinding.FragmentFavoriteBinding
import com.priambudi19.carifoto.presentation.adapter.PhotoAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
        initUi()
        mAdapter.onClick = {
            toDetailPhoto(it.id)
        }
    }

    private fun toDetailPhoto(id: String) {
        findNavController().navigate(
            R.id.action_favoriteFragment_to_detailActivity,
            bundleOf("id" to id)
        )
    }


    private fun initUi() {
        binding.apply {
            rvFavorite.apply {
                adapter = mAdapter
                setHasFixedSize(true)
            }
            favoriteToolbar.txtToolbarTitle.text = getString(R.string.list_favorite)
            favoriteToolbar.btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            lifecycleScope.launch {
                viewModel.getListFavorite().observe(viewLifecycleOwner) {
                    emptyResult.visibility = if(it.isEmpty()) View.VISIBLE else View.GONE
                    txtMessage.visibility = if(it.isEmpty()) View.VISIBLE else View.GONE
                    rvFavorite.visibility = if(it.isNotEmpty()) View.VISIBLE else View.GONE
                    mAdapter.setData(it)
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
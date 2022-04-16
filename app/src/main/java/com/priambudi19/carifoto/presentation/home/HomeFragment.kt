package com.priambudi19.carifoto.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.priambudi19.carifoto.R
import com.priambudi19.carifoto.data.model.Photo
import com.priambudi19.carifoto.databinding.HomeFragmentBinding
import com.priambudi19.carifoto.presentation.adapter.PhotoAdapter
import com.priambudi19.carifoto.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private val mAdapter: PhotoAdapter by lazy { PhotoAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun toDetailPhoto(id: String) {
        findNavController().navigate(
            R.id.action_homeFragment_to_detailActivity,
            bundleOf("id" to id)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initSearchView()
        viewModel.randomPhoto.observe(viewLifecycleOwner, ::initRandomPhoto)
    }

    private fun initSearchView() {
        binding.apply {
            btnSearch.setOnClickListener {
                val query = searchInput.editText?.text.toString()
                findNavController().navigate(
                    R.id.action_homeFragment_to_resultFragment,
                    bundleOf("query" to query)
                )
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvHome.apply {
            adapter = mAdapter
        }
    }

    private fun initRandomPhoto(resource: Resource<List<Photo>>?) {
        binding.apply {
            when (resource) {
                is Resource.Loading -> {
                    loadingHome.visibility = View.VISIBLE
                    txtMessage.visibility = View.VISIBLE
                    errorHome.visibility = View.GONE
                    txtMessage.text = getString(R.string.loading)
                }
                is Resource.Success -> {
                    loadingHome.visibility = View.GONE
                    errorHome.visibility = View.GONE
                    txtMessage.visibility = View.GONE
                    mAdapter.setData(resource.data!!)
                    mAdapter.onClick = {
                        toDetailPhoto(it.id)
                    }
                }
                is Resource.Error -> {
                    loadingHome.visibility = View.GONE
                    errorHome.visibility = View.VISIBLE
                    txtMessage.visibility = View.VISIBLE
                    txtMessage.text = getString(R.string.error_connection)
                }
                else -> Unit
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
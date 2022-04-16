package com.priambudi19.carifoto.presentation.result

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.priambudi19.carifoto.R
import com.priambudi19.carifoto.data.model.Photo
import com.priambudi19.carifoto.databinding.ResultFragmentBinding
import com.priambudi19.carifoto.presentation.adapter.PhotoPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResultFragment : Fragment() {
    private var _binding: ResultFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ResultViewModel by viewModels()
    private val mAdapter: PhotoPagingAdapter by lazy {
        PhotoPagingAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ResultFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("query")?.let { query ->
            lifecycleScope.launch {
                viewModel.query = query
                viewModel.getResult(query).observe(viewLifecycleOwner, ::initData)
                initUi()
            }
        }

    }

    private fun initUi() {
        binding.apply {
            mAdapter.addLoadStateListener {
                when(it.refresh){
                    is LoadState.NotLoading-> {
                        if (mAdapter.itemCount == 0){
                            loadingResult.visibility = View.GONE
                            errorResult.visibility = View.GONE
                            emptyResult.visibility = View.VISIBLE
                            txtMessage.visibility = View.VISIBLE
                            txtMessage.text = getString(R.string.not_found)
                            return@addLoadStateListener
                        }
                        loadingResult.visibility = View.GONE
                        errorResult.visibility = View.GONE
                        emptyResult.visibility = View.GONE
                        txtMessage.visibility = View.GONE

                    }
                    is LoadState.Error -> {
                        loadingResult.visibility = View.GONE
                        errorResult.visibility = View.VISIBLE
                        txtMessage.visibility = View.VISIBLE
                        emptyResult.visibility = View.GONE
                        txtMessage.text = getString(R.string.error_connection)
                    }
                    is LoadState.Loading->{
                        loadingResult.visibility = View.VISIBLE
                        txtMessage.visibility = View.VISIBLE
                        errorResult.visibility = View.GONE
                        emptyResult.visibility = View.GONE
                        txtMessage.text = getString(R.string.loading)
                    }
                }
            }
            rvResult.adapter = this@ResultFragment.mAdapter
            resultToolbar.btnCloseResult.setOnClickListener {
                findNavController().popBackStack()
            }
            resultToolbar.txtToolbarResult.text =
                String.format(
                    requireContext().resources.getString(R.string.hasil_pencarian),
                    viewModel.query
                )
        }
    }

    private fun initData(pagingData: PagingData<Photo>) {
        mAdapter.submitData(lifecycle, pagingData)
        mAdapter.onClick = {
            findNavController().navigate(
                R.id.action_resultFragment_to_detailActivity, bundleOf(
                    "id" to it.id
                )
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
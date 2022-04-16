package com.priambudi19.carifoto.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.priambudi19.carifoto.data.RemoteDataSource
import com.priambudi19.carifoto.data.model.Photo
import com.priambudi19.carifoto.utils.Resource
import java.io.IOException

class PhotosPagingSource(
    private val query: String,
    private val remoteDataSource: RemoteDataSource
) :
    PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key ?: 1
        return try {
            when (val result = remoteDataSource.getSearchPhoto(query, page)) {
                is Resource.Success -> {
                    val data = result.data!!
                    val prev = if (page == 1) null else page - 1
                    val next = if (data.isEmpty()) null else page + 1
                    LoadResult.Page(
                        data = data,
                        prevKey = prev,
                        nextKey = next,
                    )
                }
                is Resource.Error -> {
                    result.throwable?.printStackTrace()
                    LoadResult.Error(result.throwable!!)
                }
                else -> {
                    throw Exception("Unknown Error")
                }
            }

        } catch (e: IOException) {
            e.printStackTrace()
            LoadResult.Error(e)
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }

    }
}
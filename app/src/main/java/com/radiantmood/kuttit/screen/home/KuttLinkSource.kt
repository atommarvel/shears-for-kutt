package com.radiantmood.kuttit.screen.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.radiantmood.kuttit.data.KuttLink
import com.radiantmood.kuttit.data.RetrofitBuilder
import kotlin.math.max

class KuttLinkSource : PagingSource<Int, KuttLink>() {
    override fun getRefreshKey(state: PagingState<Int, KuttLink>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, KuttLink> {
        return try {
            val skip = params.key ?: 0
            val response = RetrofitBuilder.kuttService.getLinks(skip)
            LoadResult.Page(
                data = response.data,
                prevKey = if (skip == 0) null else max(skip - params.loadSize, 0),
                nextKey = if (response.data.size < params.loadSize) null else skip + params.loadSize
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
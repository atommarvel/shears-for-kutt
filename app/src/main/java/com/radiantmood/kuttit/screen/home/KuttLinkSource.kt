package com.radiantmood.kuttit.screen.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.radiantmood.kuttit.data.server.KuttLink
import com.radiantmood.kuttit.network.KuttService
import javax.inject.Inject
import kotlin.math.max

class KuttLinkSource @Inject constructor(
    private val kuttService: KuttService,
) : PagingSource<Int, KuttLink>() {
    override fun getRefreshKey(state: PagingState<Int, KuttLink>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, KuttLink> {
        return try {
            val skip = params.key ?: 0
            val response = kuttService.getLinks(skip)
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
package com.andre.apps.covid19updates.ui.news

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.andre.apps.covid19updates.core.feature.Result
import com.andre.apps.covid19updates.core.feature.news.model.NewsItem
import com.andre.apps.covid19updates.core.feature.news.repo.NewsRemoteRepository

class NewsPagingSource(
    private val repository: NewsRemoteRepository
) : PagingSource<Int, NewsItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsItem> {
        val nextPage = params.key ?: 1
        val response = repository.getCurrentNews(nextPage, params.loadSize)

        return when (response.status) {
            Result.Status.ERROR -> {
                LoadResult.Error(Exception(response.message!!))
            }
            Result.Status.SUCCESS -> {
                LoadResult.Page(
                    data = response.data!!.items,
                    prevKey = if (nextPage == 1) null else nextPage.dec(),
                    nextKey = nextPage.inc()
                )
            }
            else -> {
                LoadResult.Error(Exception("Should not go into this state"))
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NewsItem>): Int? {
        return state.anchorPosition
    }
}

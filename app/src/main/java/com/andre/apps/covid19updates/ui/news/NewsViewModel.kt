package com.andre.apps.covid19updates.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.FragmentNavigator
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.andre.apps.covid19updates.core.feature.news.model.NewsItem
import com.andre.apps.covid19updates.core.feature.news.repo.NewsRemoteRepository
import com.andre.apps.covid19updates.core.util.DispatcherProvider
import com.andre.apps.covid19updates.nav.NavManager
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val navManager: NavManager,
    private val repository: NewsRemoteRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    // Hate this approach tho
    val news: Flow<PagingData<NewsItem>> = Pager(PagingConfig(20)) {
        NewsPagingSource(repository)
    }.flow.cachedIn(viewModelScope)

    fun openWeb(
        url: String,
        transitionName: String,
        extras: FragmentNavigator.Extras
    ) {
        navManager.navigate(
            NewsFragmentDirections.actionNewsFragmentToWebViewFragment(
                url, transitionName
            ),
            extras
        )
    }

    override fun onCleared() {
        super.onCleared()

        viewModelScope.cancel()
    }
}

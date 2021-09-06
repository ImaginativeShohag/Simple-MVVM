package org.imaginativeworld.simplemvvm.ui.fragments.demo_postpaged

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import org.imaginativeworld.simplemvvm.datasource.PostPagingSource
import org.imaginativeworld.simplemvvm.models.DemoPost
import org.imaginativeworld.simplemvvm.repositories.AppRepository
import javax.inject.Inject

@HiltViewModel
class DemoPostPagedViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    fun getPostsPaged(): Flow<PagingData<DemoPost>> {
        return Pager(
            PagingConfig(pageSize = 20)
        ) {
            PostPagingSource(repository)
        }
            .flow
            .cachedIn(viewModelScope)
    }

}

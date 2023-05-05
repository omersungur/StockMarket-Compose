package com.omersungur.stockmarket_compose.presentation.company_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omersungur.stockmarket_compose.domain.repository.StockRepository
import com.omersungur.stockmarket_compose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListViewModel @Inject constructor(
    private val stockRepository: StockRepository,
) : ViewModel() {

    var state by mutableStateOf(CompanyListState())

    private var searchJob : Job? = null

    init {
        getCompanyList()
    }

    fun onEvent(event: CompanyListEvent) {
        when (event) {
            is CompanyListEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCompanyList()
                }
            }
            CompanyListEvent.Refresh -> {
                getCompanyList(fetchRemoteData = true)
            }
        }
    }

    private fun getCompanyList(
        query: String = state.searchQuery.lowercase(),
        fetchRemoteData: Boolean = false,
    ) {
        viewModelScope.launch {
            stockRepository.getCompanyList(
                fetchRemoteData,
                query
            ).collect { result ->
                when (result) {
                    is Resource.Error -> Unit
                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }
                    is Resource.Success -> {
                        result.data?.let { companyList ->
                            state = state.copy(companies = companyList)
                        }

                    }
                }
            }
        }
    }
}
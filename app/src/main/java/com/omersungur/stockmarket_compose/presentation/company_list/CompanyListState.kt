package com.omersungur.stockmarket_compose.presentation.company_list

import com.omersungur.stockmarket_compose.domain.model.CompanyList

data class CompanyListState(
    val companies: List<CompanyList> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
    )
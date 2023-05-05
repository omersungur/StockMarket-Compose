package com.omersungur.stockmarket_compose.presentation.company_list

sealed class CompanyListEvent {

    object Refresh: CompanyListEvent()
    data class OnSearchQueryChange(val query: String) : CompanyListEvent()
}

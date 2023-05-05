package com.omersungur.stockmarket_compose.presentation.company_info

import com.omersungur.stockmarket_compose.domain.model.CompanyInfo
import com.omersungur.stockmarket_compose.domain.model.IntradayInfo

data class CompanyInfoState(
    val stockInfoList: List<IntradayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

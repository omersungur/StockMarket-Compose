package com.omersungur.stockmarket_compose.domain.repository

import com.omersungur.stockmarket_compose.domain.model.CompanyInfo
import com.omersungur.stockmarket_compose.domain.model.CompanyList
import com.omersungur.stockmarket_compose.domain.model.IntradayInfo
import com.omersungur.stockmarket_compose.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyList(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyList>>>

    suspend fun getCompanyIntradayInfo(
        symbol: String
    ): Resource<List<IntradayInfo>>

    suspend fun getCompanyInfo(
        symbol: String
    ): Resource<CompanyInfo>
}
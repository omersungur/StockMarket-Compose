package com.omersungur.stockmarket_compose.data.mapper

import com.omersungur.stockmarket_compose.data.local.CompanyListEntity
import com.omersungur.stockmarket_compose.data.remote.dto.CompanyInfoDto
import com.omersungur.stockmarket_compose.domain.model.CompanyInfo
import com.omersungur.stockmarket_compose.domain.model.CompanyList

fun CompanyListEntity.toCompanyList() : CompanyList {
    return CompanyList(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyList.toCompanyListEntity() : CompanyListEntity {
    return CompanyListEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyInfoDto.toCompanyInfo() : CompanyInfo {
    return CompanyInfo(
        symbol = symbol ?: "", // We can get data 5 times in 1 hour(for this API). So we should make that nullable.
        country = country ?: "",
        description = description ?: "",
        industry = industry ?: "",
        name = name ?: ""
    )
}
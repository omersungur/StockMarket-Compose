package com.omersungur.stockmarket_compose.domain.model

data class CompanyInfo(
    val symbol: String,
    val name: String,
    val description: String,
    val country: String,
    val industry: String,
)

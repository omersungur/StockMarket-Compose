package com.omersungur.stockmarket_compose.di

import com.omersungur.stockmarket_compose.data.csv.CSVParser
import com.omersungur.stockmarket_compose.data.csv.CompanyIntradayInfoParser
import com.omersungur.stockmarket_compose.data.csv.CompanyListParser
import com.omersungur.stockmarket_compose.data.repository.StockRepositoryImpl
import com.omersungur.stockmarket_compose.domain.model.CompanyList
import com.omersungur.stockmarket_compose.domain.model.IntradayInfo
import com.omersungur.stockmarket_compose.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListParser(
        companyListParser: CompanyListParser
    ): CSVParser<CompanyList>

    @Binds
    @Singleton
    abstract fun bindIntradayInfoParser(
        companyIntradayInfoParser: CompanyIntradayInfoParser
    ): CSVParser<IntradayInfo>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository
}
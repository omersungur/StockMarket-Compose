package com.omersungur.stockmarket_compose.data.repository

import com.omersungur.stockmarket_compose.data.csv.CSVParser
import com.omersungur.stockmarket_compose.data.local.StockDao
import com.omersungur.stockmarket_compose.data.local.StockDatabase
import com.omersungur.stockmarket_compose.data.mapper.toCompanyInfo
import com.omersungur.stockmarket_compose.data.mapper.toCompanyList
import com.omersungur.stockmarket_compose.data.mapper.toCompanyListEntity
import com.omersungur.stockmarket_compose.data.remote.StockAPI
import com.omersungur.stockmarket_compose.domain.model.CompanyInfo
import com.omersungur.stockmarket_compose.domain.model.CompanyList
import com.omersungur.stockmarket_compose.domain.model.IntradayInfo
import com.omersungur.stockmarket_compose.domain.repository.StockRepository
import com.omersungur.stockmarket_compose.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val stockApi: StockAPI,
    stockDb: StockDatabase,
    private val companyListParser: CSVParser<CompanyList>,
    private val intradayInfoParser: CSVParser<IntradayInfo>,
) : StockRepository {

    private val dao = stockDb.stockDao

    override suspend fun getCompanyList(
        fetchFromRemote: Boolean,
        query: String,
    ): Flow<Resource<List<CompanyList>>> {
        return flow {

            emit(Resource.Loading(true))
            val localList = dao.searchCompanyList(query)
            emit(Resource.Success(
                data = localList.map { it.toCompanyList() }
            ))

            val isDbEmpty = localList.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow // we leave from flow block.
            }

            val remoteList = try {
                val response = stockApi.getCompanyList()
                companyListParser.parse(response.byteStream())

            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null

            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null // we don't have any remote data
            }

            remoteList?.let { list -> // If we get remoteList we want to save in our db.
                dao.clearCompanyList()
                dao.insertCompanyList(
                    list.map { it.toCompanyListEntity() } // Firstly, we save data which we get from api in db and then we show on ui.
                )
                emit(Resource.Success(
                    data = dao
                        .searchCompanyList("") // We list all company.
                        .map { it.toCompanyList() }
                ))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getCompanyIntradayInfo(symbol: String): Resource<List<IntradayInfo>> {
        return try {
            val response = stockApi.getIntradayInfo(symbol)
            val result = intradayInfoParser.parse(response.byteStream())
            Resource.Success(result)
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(
                message = "an error occured!")
        }catch (e:HttpException) {
            e.printStackTrace()
            Resource.Error(
                message = "an error occured!")
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val result = stockApi.getCompanyInfo(symbol)
            Resource.Success(result.toCompanyInfo())
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(
                message = "an error occured!"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(
                message = "an error occured!"
            )
        }
    }
}

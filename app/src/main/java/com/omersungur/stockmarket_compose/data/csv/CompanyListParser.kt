package com.omersungur.stockmarket_compose.data.csv

import com.omersungur.stockmarket_compose.domain.model.CompanyList
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanyListParser @Inject constructor(

): CSVParser<CompanyList> {

    override suspend fun parse(stream: InputStream): List<CompanyList> { // stream parameter holds the contents of the CSV file.
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1) // First Row (They are title)
                .mapNotNull { line -> // mapNotNull -> We want every row full. If one column is null then whole row will be null.
                    val symbol = line.getOrNull(0)
                    val name = line.getOrNull(1)
                    val exchange = line.getOrNull(2)
                    CompanyList(
                        name = name ?: return@mapNotNull null,
                        symbol = symbol ?: return@mapNotNull null,
                        exchange = exchange ?: return@mapNotNull null
                    )
                }
                .also {
                    csvReader.close()
                }
        }
    }
}
package com.omersungur.stockmarket_compose.data.csv

import android.os.Build
import androidx.annotation.RequiresApi
import com.omersungur.stockmarket_compose.data.mapper.toIntradayInfo
import com.omersungur.stockmarket_compose.data.remote.dto.IntradayInfoDto
import com.omersungur.stockmarket_compose.domain.model.CompanyList
import com.omersungur.stockmarket_compose.domain.model.IntradayInfo
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanyIntradayInfoParser @Inject constructor(

) : CSVParser<IntradayInfo> {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun parse(stream: InputStream): List<IntradayInfo> { // stream parameter holds the contents of the CSV file.
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1) // First Row (They are title)
                .mapNotNull { line -> // mapNotNull -> We want every row full. If one column is null then whole row will be null.
                    val timestamp = line.getOrNull(0) ?: return@mapNotNull null
                    val close = line.getOrNull(4) ?: return@mapNotNull null
                    val dto = IntradayInfoDto(timestamp, close.toDouble())
                    dto.toIntradayInfo()
                }.filter {
                    it.date.dayOfMonth == LocalDateTime.now().minusDays(1).dayOfMonth // we want last day's info.
                }.sortedBy {
                    it.date.hour
                }
                .also {
                    csvReader.close()
                }
        }
    }
}
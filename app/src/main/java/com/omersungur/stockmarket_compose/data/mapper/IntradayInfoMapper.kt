package com.omersungur.stockmarket_compose.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.omersungur.stockmarket_compose.data.remote.dto.IntradayInfoDto
import com.omersungur.stockmarket_compose.domain.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun IntradayInfoDto.toIntradayInfo() : IntradayInfo {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timestamp,formatter)
    return IntradayInfo(
        date = localDateTime,
        close = close
    )
}
package com.omersungur.stockmarket_compose.presentation.company_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.omersungur.stockmarket_compose.domain.model.CompanyList

@Composable
fun CompanyListItem(
    companyList: CompanyList,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = companyList.name,
                    fontSize = 16.sp,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = companyList.exchange,
                    color = Color.Black,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "(${companyList.symbol})",
                fontStyle = FontStyle.Italic,
                color = Color.Black,
            )
        }
    }
}
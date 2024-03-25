package com.easy.wallet.home.transactions.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.easy.wallet.design.theme.ThemePreviews
import com.easy.wallet.design.ui.EasyWalletTheme
import com.easy.wallet.model.TokenInformation
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.chart.scale.AutoScaleUp
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entryOf
import java.math.BigDecimal
import kotlin.random.Random

@Composable
internal fun AmountHeaderView(
    modifier: Modifier = Modifier,
    tokenInformation: TokenInformation?,
    balance: String,
    rate: BigDecimal = BigDecimal.ONE,
    trends: List<String>
) {
    val chartEntryModelProducer by remember(trends) {
        val trendValues = trends.map { it.toFloatOrNull() ?: 0.0f }
        mutableStateOf(ChartEntryModelProducer(trendValues.mapIndexed { index, value -> entryOf(index, value) }))
    }
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopStart
    ) {
        Chart(
            modifier = Modifier
                .fillMaxHeight(1.0f)
                .align(Alignment.CenterStart),
            chart = lineChart(),
            chartModelProducer = chartEntryModelProducer,
            isZoomEnabled = false,
            autoScaleUp = AutoScaleUp.Full
        )
        Column(
            modifier = Modifier
                .padding(12.dp)
                .background(
                    MaterialTheme.colorScheme.surfaceTint.copy(alpha = 0.5f),
                    RoundedCornerShape(8.dp)
                )
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$balance ${tokenInformation?.symbol}",
                style = MaterialTheme.typography.headlineLarge
            )
//            Text(text = "¥ ${balance.toBigDecimal().times(rate)}")
        }
    }
}

@ThemePreviews
@Composable
private fun AmountHeader_Preview() {
    EasyWalletTheme {
        Surface {
            AmountHeaderView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp),
                tokenInformation = TokenInformation(
                    "",
                    "Ethereum",
                    "Ethereum",
                    "ETH",
                    18,
                    null,
                    "",
                    true
                ),
                balance = "12345678",
                trends = List(8) { Random.nextInt(12).toString() }
            )
        }
    }
}

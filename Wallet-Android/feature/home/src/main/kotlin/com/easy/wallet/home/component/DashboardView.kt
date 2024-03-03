package com.easy.wallet.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.chart.scale.AutoScaleUp
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryOf
import kotlin.random.Random

@Composable
internal fun DashboardView(
    modifier: Modifier = Modifier,
    charDatas: List<FloatEntry>
) {
    val chartEntryModelProducer by remember {
        mutableStateOf(ChartEntryModelProducer(charDatas))
    }
    Box(modifier = modifier.padding(vertical = 16.dp)) {
        Chart(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(1.0f)
                .align(Alignment.CenterEnd),
            chart = lineChart(),
            chartModelProducer = chartEntryModelProducer,
            isZoomEnabled = false,
            autoScaleUp = AutoScaleUp.Full
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .fillMaxHeight()
                .padding(start = 16.dp)
                .align(Alignment.CenterStart),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "888.88 USD", style = MaterialTheme.typography.headlineLarge)
            Text(
                text = "9.9%",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primaryContainer
            )
        }
    }
}

@ThemePreviews
@Composable
private fun DashboardView_Preview() {
    EasyWalletTheme {
        DashboardView(
            modifier = Modifier
                .height(248.dp)
                .fillMaxWidth(),
            List(8) { entryOf(it, Random.nextFloat() * 16f) }
        )
    }
}

package com.easy.wallet.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.easy.wallet.design.theme.GradientColors
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entryOf
import kotlin.random.Random

private fun getRandomEntries() = List(4) { entryOf(it, Random.nextFloat() * 16f) }

@Composable
internal fun DashboardView(
    modifier: Modifier = Modifier
) {
    val chartEntryModelProducer by remember {
        mutableStateOf(ChartEntryModelProducer(getRandomEntries()))
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(0.6f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "888.88 USD", style = MaterialTheme.typography.titleLarge)
            Text(text = "9.9%")
        }
        Chart(
            modifier = Modifier
                .weight(0.4f)
                .aspectRatio(1.5f),
            chart = lineChart(),
            chartModelProducer = chartEntryModelProducer
        )
    }
}

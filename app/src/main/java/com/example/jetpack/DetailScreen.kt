package com.example.jetpack

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    count: Int,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Halaman Detail Counter",
        )
        Text(
            text = "Nilai Counter Terakhir $count",
        )
        Button(
            onClick = onBackClick
        ) {
            Text(
                text = "Kembali Ke Counter"
            )
        }
    }
}
package com.example.jetpack

import android.os.Bundle
import android.provider.CalendarContract.Colors
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpack.ui.theme.JetpackTheme

@Composable
fun CounterScreen(
    modifier: Modifier = Modifier,
    viewModel: CounterViewModel = viewModel(),
    onNavigateToDetail: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    CounterContent(
        modifier,
        uiState.count,
        { viewModel.onIncrement() },
        { viewModel.onReset() },
        uiState.isIncrementEnabled,
        uiState.isResetEnabled,
        uiState.stepText,
        { viewModel.onStepChange(it) },
        { onNavigateToDetail(uiState.count) }
    )
}

@Composable
fun CounterContent(
    modifier: Modifier = Modifier,
    count: Int,
    onIncrement: () -> Unit,
    onReset: () -> Unit,
    isIncrementEnabled: Boolean,
    isResetEnabled: Boolean,
    stepText: String,
    onStepChange: (String) -> Unit,
    onNavigateToDetail: (Int) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "$count",
            fontSize = 32.sp
        )
        OutlinedTextField(
            value = stepText,
            onValueChange = onStepChange,
            label = { Text("Step / Multiple") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Button(
            onClick = onIncrement,
            enabled = isIncrementEnabled
        ) {
            if (!isIncrementEnabled) {
                Text(
                    "Limit Reached!",
                    color = Color.Red,
                    fontSize = 14.sp
                )
            } else Text("Click Me!")
        }
        Button(
            onClick = onReset,
            enabled = isResetEnabled
        ) {
            Text("Reset Count")
        }
        Button(
            onClick = { onNavigateToDetail(count) }
        ) {
            Text("Lihat Detail")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CounterPreviewWithLargeNumber() {
    JetpackTheme {
        CounterContent(Modifier, 10, {}, {}, false, false, "", {}, {})
    }
}
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
import com.example.jetpack.ui.theme.JetpackTheme

@Composable
fun CounterScreen(modifier: Modifier = Modifier) {
    var count by rememberSaveable { mutableIntStateOf(0) }
    var stepText by rememberSaveable { mutableStateOf("1") }
    val step = stepText.toIntOrNull() ?: 1

    val isIncrementEnabled by remember {
        derivedStateOf { count < 10 }
    }
    val isResetEnabled by remember {
        derivedStateOf { count > 0 }
    }

    CounterContent(
        modifier,
        count,
        { count += step },
        { count = 0 },
        isIncrementEnabled,
        isResetEnabled,
        stepText,
        { stepText = it }
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
    onStepChange: (String) -> Unit
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
    }
}

@Preview(showBackground = true)
@Composable
fun CounterPreviewWithLargeNumber() {
    JetpackTheme {
        CounterContent(Modifier, 10, {}, {}, false, false, "", {})
    }
}
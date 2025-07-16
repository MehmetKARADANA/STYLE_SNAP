package com.mobile.stylesnap.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(onClick: () -> Unit, text: String) {
    Button(modifier = Modifier.fillMaxWidth().padding(8.dp), onClick = {
        onClick.invoke()
    }) {
        Text(text)
    }
}
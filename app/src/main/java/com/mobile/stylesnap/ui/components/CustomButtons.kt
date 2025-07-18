package com.mobile.stylesnap.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobile.stylesnap.ui.theme.buttonColor

@Composable
fun CustomButton(onClick: () -> Unit, text: String) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = buttonColor,
        onClick = {
            onClick.invoke()
        }) {
        Text(text)
    }
}

@Composable
fun CustomMiniButton(onClick: () -> Unit, text: String,enabled : Boolean?) {
    if (enabled != null) {
        Button(
            modifier = Modifier
                .padding(8.dp),
            enabled = enabled,
            shape = RoundedCornerShape(16.dp),
            colors = buttonColor,
            onClick = {
                onClick.invoke()
            }) {
            Text(text)
        }
    }
}
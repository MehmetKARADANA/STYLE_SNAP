package com.mobile.stylesnap.ui.components

import android.preference.PreferenceActivity.Header
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobile.stylesnap.ui.theme.BackgroundColor
import com.mobile.stylesnap.ui.theme.textColor

@Composable
fun Header(title: String) {
    val statusBarColor = BackgroundColor
    val activity = LocalActivity.current
    activity?.window?.statusBarColor = statusBarColor.toArgb()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .alpha(1f)
          .background(BackgroundColor)
        ,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = textColor
        )

    }
}
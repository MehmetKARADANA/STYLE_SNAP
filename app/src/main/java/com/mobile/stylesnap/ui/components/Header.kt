package com.mobile.stylesnap.ui.components

import android.preference.PreferenceActivity.Header
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

@Composable
fun Header(title: String) {
    /*val statusBarColor = LightBackground
    val activity = LocalActivity.current
    activity?.window?.statusBarColor = statusBarColor.toArgb()*/

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .alpha(1f)
        //  .background(LightBackground)
        ,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title)
    }
}
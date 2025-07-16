package com.mobile.stylesnap.ui.screens

import android.annotation.SuppressLint
import android.icu.text.CaseMap.Title
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mobile.stylesnap.ImagePickerHelper
import com.mobile.stylesnap.ImageSaver
import com.mobile.stylesnap.R
import com.mobile.stylesnap.StyleModelList
import com.mobile.stylesnap.ui.components.CustomButton
import com.mobile.stylesnap.ui.components.Header
import com.mobile.stylesnap.viewmodels.StyleViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(viewModel: StyleViewModel) {
    val context = LocalContext.current

    val originalImage by viewModel.originalBitmap.collectAsState()
    val stylizedImage by viewModel.stylizedBitmap.collectAsState()
    val isProcessing by viewModel.isProcessing.collectAsState()

    var selectedModel by remember { mutableStateOf(StyleModelList.models.first()) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val bitmap = ImagePickerHelper.uriToBitmap(context, it)
            viewModel.setOriginalBitmap(bitmap)
        }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues()),
        topBar = {
            Header(title = "Style Snap")
        }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {

                if (originalImage != null) {
                    Image(
                        bitmap = originalImage!!.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                    )
                } else {
                    Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center){
                        Image(
                            painter = painterResource(id = R.drawable.gallery),
                            contentDescription = null,
                            modifier = Modifier
                                .height(100.dp)
                                .fillMaxWidth()
                        )
                    }

                }

                //  Spacer(modifier = Modifier.height(16.dp))
                CustomButton(onClick = { imagePickerLauncher.launch("image/*") }, text = "Fotoğraf Seç")

             //   Spacer(modifier = Modifier.height(16.dp))

                Text("Stil Seç:")
                Row {
                    StyleModelList.models.forEach { modelName ->
                        Button(
                            onClick = { selectedModel = modelName },
                            modifier = Modifier.padding(horizontal = 4.dp)
                        ) {
                            Text(modelName.removeSuffix(".tflite"))
                        }
                    }
                }


                Spacer(modifier = Modifier.height(16.dp))

                if (isProcessing) {
                    CircularProgressIndicator()
                } else {
                    stylizedImage?.let { image ->
                        Text("Sonuç:")
                        Spacer(modifier = Modifier.height(8.dp))

                        Image(
                            bitmap = image.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(onClick = {
                            val success = ImageSaver.saveBitmapToGallery(context, image)
                            Toast.makeText(
                                context,
                                if (success) "Kaydedildi 📷" else "Kaydedilemedi!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }) {
                            Text("Galeriye Kaydet")
                        }
                    }

                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { viewModel.applyStyle(selectedModel) },
                    enabled = originalImage != null && !isProcessing
                ) {
                    Text("Stil Uygula")
                }
            }
        }
    }

}

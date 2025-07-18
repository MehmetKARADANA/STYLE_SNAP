package com.mobile.stylesnap.ui.screens

import android.annotation.SuppressLint
import android.icu.text.CaseMap.Title
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobile.stylesnap.ImagePickerHelper
import com.mobile.stylesnap.ImageSaver
import com.mobile.stylesnap.R
import com.mobile.stylesnap.StyleModelList
import com.mobile.stylesnap.ui.components.CustomButton
import com.mobile.stylesnap.ui.components.CustomMiniButton
import com.mobile.stylesnap.ui.components.Header
import com.mobile.stylesnap.ui.theme.BackgroundColor
import com.mobile.stylesnap.ui.theme.textColor
import com.mobile.stylesnap.viewmodels.StyleViewModel
import java.util.Locale

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
            .background(BackgroundColor)
            .padding(WindowInsets.systemBars.asPaddingValues()),
        topBar = {
            Header(title = "Style Snap")
        }) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(BackgroundColor)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (originalImage != null) {
                    Image(
                        bitmap = originalImage!!.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.gallery),
                            contentDescription = null,
                            modifier = Modifier
                                .height(100.dp)
                                .fillMaxWidth()
                        )
                    }

                }

                CustomButton(
                    onClick = { imagePickerLauncher.launch("image/*") },
                    text = "Fotoğraf Seç"
                )
                Text(
                    text = "Stil Seç:",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = textColor,
                    modifier = Modifier.padding(top = 16.dp)
                )




                  LazyRow {
                      items(StyleModelList.models){
                      modelName ->
                      val name = modelName.removeSuffix(".tflite").capitalize(Locale.ROOT)
                      CustomMiniButton(onClick = {
                          selectedModel = modelName
                      }, text = name, enabled = true)
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
                                .size(200.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        CustomButton(onClick = {
                            val success = ImageSaver.saveBitmapToGallery(context, image)
                            Toast.makeText(
                                context,
                                if (success) "Kaydedildi 📷" else "Kaydedilemedi!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }, text = "Galeriye Kaydet")
                    }

                }
                Spacer(modifier = Modifier.height(16.dp))
                CustomMiniButton(onClick = {
                    viewModel.applyStyle(selectedModel)
                }, text = "Stil Uygula", enabled = originalImage != null && !isProcessing)

            }
        }
    }

}

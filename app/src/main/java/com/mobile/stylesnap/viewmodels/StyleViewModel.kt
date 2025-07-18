package com.mobile.stylesnap.viewmodels

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.stylesnap.repositorys.StyleTransferRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class StyleViewModel(context: Context) : ViewModel() {

    private val repository = StyleTransferRepository(context)

    private val _originalBitmap = MutableStateFlow<Bitmap?>(null)
    val originalBitmap: StateFlow<Bitmap?> = _originalBitmap

    private val _stylizedBitmap = MutableStateFlow<Bitmap?>(null)
    val stylizedBitmap: StateFlow<Bitmap?> = _stylizedBitmap

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> = _isProcessing
    fun setOriginalBitmap(bitmap: Bitmap) {
        _originalBitmap.value = bitmap
    }

    fun applyStyle(modelFileName: String) {
        val bitmap = _originalBitmap.value ?: return

        viewModelScope.launch(Dispatchers.Default) {
            _isProcessing.value = true
            val output = repository.applyStyle(bitmap, modelFileName)
            _stylizedBitmap.value = output
            _isProcessing.value = false
        }
    }


}
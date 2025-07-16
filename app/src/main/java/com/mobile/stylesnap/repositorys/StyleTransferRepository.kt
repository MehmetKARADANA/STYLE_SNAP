package com.mobile.stylesnap.repositorys

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import androidx.core.graphics.set

class StyleTransferRepository(private val context: Context) {

    private fun loadModelFile(fileName: String): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd(fileName)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, fileDescriptor.startOffset, fileDescriptor.length)
    }

    fun applyStyle(contentBitmap: Bitmap, modelName: String): Bitmap {
        val modelBuffer = loadModelFile(modelName)
        val interpreter = Interpreter(modelBuffer)

        val inputShape = interpreter.getInputTensor(0).shape() // [1, H, W, 3] or [1, 3, H, W]
        val outputShape = interpreter.getOutputTensor(0).shape() // Check actual output shape

        // Determine input dimensions based on shape
        val inputHeight: Int
        val inputWidth: Int
        val inputChannels: Int

        if (inputShape.size == 4) {
            if (inputShape[3] == 3) {
                // Format: [batch, height, width, channels]
                inputHeight = inputShape[1]
                inputWidth = inputShape[2]
                inputChannels = inputShape[3]
            } else {
                // Format: [batch, channels, height, width]
                inputChannels = inputShape[1]
                inputHeight = inputShape[2]
                inputWidth = inputShape[3]
            }
        } else {
            throw IllegalArgumentException("Unexpected input shape: ${inputShape.contentToString()}")
        }

        val resized = Bitmap.createScaledBitmap(contentBitmap, inputWidth, inputHeight, true)
        val input = convertBitmapToByteBuffer(resized, inputWidth, inputHeight, inputChannels)

        // Create output array with correct shape matching the model's output
        val output = if (outputShape[1] == 3) {
            // Output format: [batch, channels, height, width]
            Array(1) { Array(3) { Array(outputShape[2]) { FloatArray(outputShape[3]) } } }
        } else {
            // Output format: [batch, height, width, channels]
            Array(1) { Array(outputShape[1]) { Array(outputShape[2]) { FloatArray(outputShape[3]) } } }
        }

        interpreter.run(input, output)

        return convertOutputToBitmap(output, outputShape)
    }

    private fun convertBitmapToByteBuffer(bitmap: Bitmap, width: Int, height: Int, channels: Int): ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(1 * width * height * channels * 4) // float32
        byteBuffer.order(ByteOrder.nativeOrder())

        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        for (pixel in pixels) {
            val r = (pixel shr 16 and 0xFF) / 255.0f
            val g = (pixel shr 8 and 0xFF) / 255.0f
            val b = (pixel and 0xFF) / 255.0f
            byteBuffer.putFloat(r)
            byteBuffer.putFloat(g)
            byteBuffer.putFloat(b)
        }

        return byteBuffer
    }

    private fun convertOutputToBitmap(output: Array<Array<Array<FloatArray>>>, outputShape: IntArray): Bitmap {
        val width: Int
        val height: Int

        if (outputShape[1] == 3) {
            // Format: [batch, channels, height, width]
            height = outputShape[2]
            width = outputShape[3]

            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            for (y in 0 until height) {
                for (x in 0 until width) {
                    val r = (output[0][0][y][x] * 255).toInt().coerceIn(0, 255)
                    val g = (output[0][1][y][x] * 255).toInt().coerceIn(0, 255)
                    val b = (output[0][2][y][x] * 255).toInt().coerceIn(0, 255)
                    bmp.setPixel(x, y, Color.rgb(r, g, b))
                }
            }
            return bmp
        } else {
            // Format: [batch, height, width, channels]
            height = outputShape[1]
            width = outputShape[2]

            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            for (y in 0 until height) {
                for (x in 0 until width) {
                    val r = (output[0][y][x][0] * 255).toInt().coerceIn(0, 255)
                    val g = (output[0][y][x][1] * 255).toInt().coerceIn(0, 255)
                    val b = (output[0][y][x][2] * 255).toInt().coerceIn(0, 255)
                    bmp.setPixel(x, y, Color.rgb(r, g, b))
                }
            }
            return bmp
        }
    }
}
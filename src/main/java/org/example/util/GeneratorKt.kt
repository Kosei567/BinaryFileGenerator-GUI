package org.example.util

import java.io.FileOutputStream
import java.io.IOException

private interface SizeRandom{
    fun generate(onProgress: (Double) -> Unit): Int
}

open class FileMaterials(val fileName: String, val extension: String)

private fun fileOut(size: Int, fileName: String, extension: String, onProgress: (Double) -> Unit){
    val realFileName = "$fileName.$extension"
    val realSize = size *1000
    val data = ByteArray(realSize)
    for (i in 1..realSize){
        val percent: Double = i / realSize.toDouble()
        val asciiCode = Math.random() * (176 - 33) + 33
        val ascii = asciiCode.toInt()
        data[i - 1] = ascii.toByte()
        onProgress(percent)
    }
    try{
        FileOutputStream(realFileName).use { fos ->
            fos.write(data)
            println("${java.nio.file.Paths.get(realFileName).toAbsolutePath()}")
            fos.close()
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

class RandomSize(fileName: String, extension: String, private val randomA: Int, private val randomB: Int): FileMaterials(fileName, extension), SizeRandom{

    override fun generate(onProgress: (Double) -> Unit): Int {
        val random = Math.random () * (randomB - randomA) + randomA
        val size = random.toInt()
        fileOut(size, fileName, extension, onProgress)
        return size
    }

}

class FixationSize(fileName: String, extension: String, private val fixationSize: Int): FileMaterials(fileName, extension), SizeRandom{

    override fun generate(onProgress: (Double) -> Unit): Int {
        val size = fixationSize
        fileOut(size, fileName, extension, onProgress)
        return size
    }
}
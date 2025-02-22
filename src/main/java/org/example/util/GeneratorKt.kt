package org.example.util

import java.io.FileOutputStream
import java.io.IOException

private interface SizeRandom{
    fun generate(onProgress: (Float) -> Unit): Int
}

open class FileMeterials(val fileName: String, val extension: String)

private fun fileOut(size: Int, fileName: String, extension: String, onProgress: (Float) -> Unit){
    val filename = "$fileName.$extension"
    val realSize = size *1000
    val data = ByteArray(realSize)
    for (i in 1..realSize){
        val percent: Float = i / realSize.toFloat()
        val asciicode = Math.random() * (176 - 33) + 33
        val ascii = asciicode.toInt()
        data[i - 1] = ascii.toByte()
        onProgress(percent)
    }
    try{
        FileOutputStream(filename).use { fos ->
            fos.write(data)
            //print("Binary file generated successfully.\n File size is " + size + "KB")
            println("${java.nio.file.Paths.get(fileName).toAbsolutePath()}")
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

class Random(fileName: String, extension: String, private val randomA: Int, private val randomB: Int): FileMeterials(fileName, extension), SizeRandom{

    override fun generate(onProgress: (Float) -> Unit): Int {
        val random = Math.random () * (randomB - randomA) + randomA
        val size = random.toInt()
        fileOut(size, fileName, extension, onProgress)
        return size
    }

}

class Fixation(fileName: String, extension: String, private val fixationSize: Int): FileMeterials(fileName, extension), SizeRandom{

    override fun generate(onProgress: (Float) -> Unit): Int {
        val size = fixationSize
        fileOut(size, fileName, extension, onProgress)
        return size
    }
}
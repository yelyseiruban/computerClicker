package com.example.clicker

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.res.Resources.NotFoundException
import android.widget.Toast
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader

class FileManager (private val context: Context) {
    fun save(fileName: String, content: String){
        var fos: FileOutputStream? = null
        try {
            fos = context.openFileOutput(fileName, MODE_PRIVATE)
            fos.write(content.toByteArray())
            Toast.makeText(context, "Saved to ${context.filesDir}/$fileName", Toast.LENGTH_LONG).show()
        } catch (e: NotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fos != null){
                try {
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun load(fileName: String): String? {
        var content: String? = null
        var fis : FileInputStream? = null
        try {
            fis = context.openFileInput(fileName)
            val inputStreamReader: InputStreamReader = InputStreamReader(fis)
            val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder: StringBuilder = StringBuilder()
            var line: String?

            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            content = stringBuilder.toString()

        } catch (e: FileNotFoundException){
            e.printStackTrace()
        } catch (e: IOException){
            e.printStackTrace()
        }
        finally {
            if (fis != null){
                try {
                    fis.close()
                } catch (e: IOException){
                    e.printStackTrace()
                }
            }
        }
        return content
    }
}

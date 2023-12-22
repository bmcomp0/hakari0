//package com.example.scalesseparatefileble.data
//
//import android.content.ContentValues
//import android.content.Context
//import android.net.Uri
//import android.os.Environment
//import android.provider.MediaStore
//import com.opencsv.CSVReader
//import java.io.File
//import java.io.FileReader
//import java.io.OutputStream
//
//class MediaStoreCSV {
//    fun saveToCsv(data: List<String>, context: Context, label: String) {
//        try {
//            val resolver = context.contentResolver
//
//            // Define the values for the new CSV file
//            val contentValues = ContentValues().apply {
//                put(MediaStore.Downloads.DISPLAY_NAME, "data.csv")
//                put(MediaStore.Downloads.MIME_TYPE, "text/csv")
//                put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
//            }
//
//            // Insert into MediaStore.Downloads to get a content URI
//            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues) ?: return
//
//            // Open an OutputStream using the content URI
//            val outputStream: OutputStream = resolver.openOutputStream(uri) ?: return
//
//            // Use BufferedWriter for efficient writing
//            outputStream.bufferedWriter().use { writer ->
//                writer.write("index,$label")
//                writer.newLine()
//
//                data.forEachIndexed { index, weight ->
//                    writer.write("$index, $weight")
//                    writer.newLine()
//                }
//            }
//
//            // Close the OutputStream
//            outputStream.close()
//
//            println("Data saved to CSV file: ${uri.toString()}")
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    fun readCsvFile(uri: String, context: Context): Array<Array<String>> {
//        val dataRows = mutableListOf<Array<String>>()
//
//        try {
//            val inputStream = context.contentResolver.openInputStream(Uri.parse(uri)) ?: return emptyArray()
//
//            // Create a CSVReader object
//            val csvReader = CSVReader(FileReader(inputStream.bufferedReader()))
//
//            // Process each row in the CSV file
//            var record: Array<String>?
//            while (csvReader.readNext().also { record = it } != null) {
//                dataRows.add(record ?: continue)
//            }
//
//            // Close the CSVReader
//            csvReader.close()
//            inputStream.close()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return dataRows.toTypedArray()
//    }
//}

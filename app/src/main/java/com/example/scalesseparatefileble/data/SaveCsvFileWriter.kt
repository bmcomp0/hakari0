package com.example.scalesseparatefileble.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import com.example.scalesseparatefileble.util.ColumnItem
import com.opencsv.CSVReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

class SaveCsvFileWriter {
    fun saveToCsv(data: List<ColumnItem>, context: Context, label: String): String {
        try {
            val fileName = "data_${label}.csv"
            val resolver = context.contentResolver

            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, "text/csv")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS + "/MyApp")
            }

            val uri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)

            uri?.let {
                resolver.openOutputStream(it)?.use { outputStream ->
                    // Write the header if file is newly created
                    outputStream.bufferedWriter().use { writer ->
                        writer.write("$label : id, value")
                        writer.newLine()
                        data.forEachIndexed { index, columnItem ->
                            writer.write("${index + 1},${columnItem.value}")
                            writer.newLine()
                        }
                    }
                }

                return uri.toString()
            }

            return ""
        } catch (e: IOException) {
            e.printStackTrace()
            return ""
        }
    }

    fun readCsvFile(context: Context, filename: String): List<ColumnItem> {
        val dataRows = mutableListOf<ColumnItem>()

        try {
            val resolver = context.contentResolver

            // Define the projection (the columns to retrieve)
            val projection = arrayOf(
                MediaStore.MediaColumns._ID,
                MediaStore.MediaColumns.DISPLAY_NAME
            )

            // Define the selection criteria
            val selection = "${MediaStore.MediaColumns.DISPLAY_NAME} = ? AND ${MediaStore.MediaColumns.RELATIVE_PATH} LIKE ?"
            val selectionArgs = arrayOf(filename, "%${Environment.DIRECTORY_DOCUMENTS}/MyApp%")

            // Query the file using the content resolver
            val cursor: Cursor? = resolver.query(
                MediaStore.Files.getContentUri("external"),
                projection,
                selection,
                selectionArgs,
                null
            )

            // Process the query result
            cursor?.use {
                if (it.moveToFirst()) {
                    // Get the URI of the file
                    val id = it.getLong(it.getColumnIndexOrThrow(MediaStore.MediaColumns._ID))
                    val uri = Uri.withAppendedPath(MediaStore.Files.getContentUri("external"), id.toString())

                    // Open an input stream for the content URI
                    val inputStream = context.contentResolver.openInputStream(uri) ?: throw IOException("Cannot open input stream")
                    val csvReader = CSVReader(InputStreamReader(inputStream))

                    // Read the header line (if any)
                    val headers = csvReader.readNext()

                    // Process each row in the CSV file
                    var record: Array<String>?
                    while (csvReader.readNext().also { record = it } != null) {
                        // Process the data in each row
                        val id = UUID.randomUUID()
                        val value = record?.get(1) ?: ""
                        dataRows.add(ColumnItem(id = id, value = value))
                    }

                    // Close the CSVReader
                    csvReader.close()
                }
            }
        } catch (e: IOException) {
            // Handle IOException, such as file not found or other issues
            e.printStackTrace()
        }

        return dataRows
    }

    fun getFiles(context: Context): List<String> {
        val fileNames = mutableListOf<String>()

        val projection = arrayOf(
            MediaStore.MediaColumns.DISPLAY_NAME,
            MediaStore.MediaColumns._ID
        )

        val selection = "${MediaStore.MediaColumns.RELATIVE_PATH} LIKE ?"
        val selectionArgs = arrayOf("${Environment.DIRECTORY_DOCUMENTS}/MyApp/%")

        val queryUri = MediaStore.Files.getContentUri("external")
        val cursor: Cursor? = context.contentResolver.query(
            queryUri,
            projection,
            selection,
            selectionArgs,
            null
        )

        cursor?.use {
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
            while (cursor.moveToNext()) {
                val fileName = cursor.getString(nameColumn)
                fileNames.add(fileName)
            }
        }

        return fileNames
    }
}
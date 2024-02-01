package com.example.scalesseparatefileble.data

import android.content.Context
import android.os.Environment
import com.example.scalesseparatefileble.util.ColumnItem
import com.opencsv.CSVReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.util.*

class SaveCsvFileWriter {
    fun saveToCsv(data: List<ColumnItem>, context: Context, label: String) {
        try {
            // Get the external storage directory
            val externalStorageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)

            // Create a CSV file
            val csvFile = File(externalStorageDir, "data_${label}.csv")

            // If the file doesn't exist, create a new one with the header
            if (!csvFile.exists()) {
                csvFile.createNewFile()
                csvFile.bufferedWriter().use { writer ->
                    writer.write("$label : id, value")
                    writer.newLine()
                }
            }

            // Read existing data from the file
            val existingData = readCsvFile(csvFile)

            // Combine the existing data with the new data
            val combinedData = existingData + data

            // Write the combined data back to the file
            csvFile.bufferedWriter().use { writer ->
                writer.write("$label : index, value")
                writer.newLine()
                combinedData.forEachIndexed { index, columnItem ->
                    writer.write("${index+1},${columnItem.value}")
                    writer.newLine()
                }
            }

            // Notify the user that the data has been modified
            println("Data modified in CSV file: ${csvFile.absolutePath}")
        } catch (e: IOException) {
            // Handle the exception
            e.printStackTrace()
        }
    }

    fun readCsvFile(csvFile: File): List<ColumnItem> {
//        val dataRows = mutableListOf<Array<String>>()
        val dataRows = mutableListOf<ColumnItem>()

        try {
            // Create a CSVReader object
            val csvReader = CSVReader(FileReader(csvFile))

            // Read the header line (if any)
            val headers = csvReader.readNext()

            // Process each row in the CSV file
            var record: Array<String>?
            while (csvReader.readNext().also { record = it } != null) {
                println(record?.get(0))
                // Process the data in each row
                val id = UUID.randomUUID()
                val value = record?.get(1) ?: ""
                dataRows.add(ColumnItem(id = id, value = value))
            }

            // Close the CSVReader
            csvReader.close()
        } catch (e: IOException) {
            // Handle IOException, such as file not found or other issues
            e.printStackTrace()
        }
        return dataRows
    }

    fun getFiles(context: Context): List<String> {
        val files: Array<File>
        val fileNames = mutableListOf<String>()

        val externalStorageDir: File = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!
        files = externalStorageDir.listFiles() ?: arrayOf()

        for (file in files) {
            fileNames.add(file.name)
        }

        return fileNames
    }

    fun deleteAllFiles(context: Context){
        val files: Array<File>?
        val message: String

        val text = java.lang.StringBuilder("\n")
        val externalStorageDir: File = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!
        files = externalStorageDir.listFiles()
        if (files != null) {
            for (file in files) {
                text.append(file.name).append("\n")
                file.delete()
            }
        }
        message = "deleted files = [$text]"
        println(message)
    }
}
package com.example.scalesseparatefileble.data

import android.content.Context
import android.os.Environment
import com.opencsv.CSVReader
import java.io.File
import java.io.FileReader
import java.io.IOException

class SaveCsvFileWriter {
    fun saveToCsv(data: List<String>, context: Context, label: String) {
        try {

            var files: Array<String> = context.fileList()
            println("Files: ${files.joinToString(", ")}")

            // Get the external storage directory
            val externalStorageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
//            val externalStorageDir = context.filesDir

            // Create a CSV file
            val csvFile = File(externalStorageDir, "data.csv")
            csvFile.createNewFile()

            // Use BufferedWriter for efficient writing
            csvFile.bufferedWriter().use { writer ->
                // Write header
//                writer.write("Weight")
                writer.write("index,$label")
                writer.newLine()

                // Write data to the CSV file
                data.forEachIndexed { index, weight ->
                    writer.write("$index, $weight")
                    writer.newLine()
                }
            }

            // Notify the user that the data has been saved
            // (You might want to use a Toast or Snackbar for a better user experience)
            println("Data saved to CSV file: ${csvFile.absolutePath}")
        } catch (e: IOException) {
            // Handle the exception
            e.printStackTrace()
        }
    }

    fun readCsvFile(csvFile: File): Array<Array<String>> {
        val dataRows = mutableListOf<Array<String>>()

        try {
            // Create a CSVReader object
            val csvReader = CSVReader(FileReader(csvFile))

            // Read the header line (if any)
//            val headers = csvReader.readNext()

            // Process each row in the CSV file
            var record: Array<String>?
            while (csvReader.readNext().also { record = it } != null) {
                // Process the data in each row
                dataRows.add(record ?: continue)

            }

            // Close the CSVReader
            csvReader.close()
        } catch (e: IOException) {
            // Handle IOException, such as file not found or other issues
            e.printStackTrace()
        }
        return dataRows.toTypedArray()
    }
}
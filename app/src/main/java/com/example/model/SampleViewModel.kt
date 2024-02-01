package com.example.model

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scalesseparatefileble.data.SaveCsvFileWriter
import com.example.scalesseparatefileble.util.ColumnItem
import java.io.File
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    @ApplicationContext context: Context
    ) : ViewModel() {
    val devices = MutableLiveData<MutableList<String>>(mutableStateListOf())
//    val dataList = mutableStateListOf<String>()
    private val context = context
    private val saveCsvFile = SaveCsvFileWriter()
    val label = mutableStateOf("")

    val csvFileList = mutableListOf<String>()
    val csvDataList = mutableListOf<ColumnItem>()
    val readingCsvFile = mutableStateOf<String>("")

    val items = mutableStateListOf<ColumnItem>()
    private val lastRemovedItems = mutableListOf<Pair<ColumnItem, Int>>() // アイテムとその元のインデックスを保持

    // 項目を追加
    fun addItem(value: String) {
        val newItem = ColumnItem(value = value)
        items.add(newItem)
    }

    // 項目を削除
    fun removeItem(item: ColumnItem) {
        val index = items.indexOf(item)
        lastRemovedItems.add(item to index) // アイテムとそのインデックスを保存
        items.remove(item)
    }

    // 最後に削除された項目を復元
    fun undoRemoval() {
        if (lastRemovedItems.isNotEmpty()) {
            val (itemToRestore, index) = lastRemovedItems.removeLast()
            items.add(index, itemToRestore) // 元のインデックスにアイテムを挿入
        }
    }

    init {
        // Request WRITE_EXTERNAL_STORAGE permission
        val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val requestCode = 123 // You can use any unique code
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context as Activity, arrayOf(permission), requestCode)
        }
        saveCsvFile.getFiles(context)
    }

    fun saveDataCsv(){
        try {
            saveCsvFile.saveToCsv(items, context, label.value)
            Toast.makeText(context, "保存しました", Toast.LENGTH_SHORT).show()
        }catch (e: IOException) {
            // Handle the exception and show a Toast for failure
            Toast.makeText(context, "保存できませんでした", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    fun readDataCsv(fileName: String){
        readingCsvFile.value = fileName

        val externalStorageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val csvFile = File(externalStorageDir, fileName)
        val data = saveCsvFile.readCsvFile(csvFile)
        // Print the returned array (just for demonstration)
        data.forEach { columnItem ->
            println("data: id=${columnItem.id}, text=${columnItem.value}")
        }
        csvDataList.clear()  // Clear existing data
        csvDataList.addAll(data)  // Add new data
    }

    fun addDevice(deviceName: String) {
        if (devices.value?.contains(deviceName) == false) {
            devices.value?.add(deviceName)
        }
    }

    fun getFileList(){
        val data = saveCsvFile.getFiles(context)
        // Print the returned array (just for demonstration)
        data.forEach { text ->
            println("$text")
        }
        csvFileList.clear()  // Clear existing data
        csvFileList.addAll(data)  // Add new data
    }
}

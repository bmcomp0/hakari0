package com.example.model

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import androidx.compose.runtime.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scalesseparatefileble.data.SaveCsvFileWriter
import kotlinx.coroutines.launch
import java.io.File
import java.util.Arrays

class SampleViewModel(context: Context) : ViewModel() {
    val items = MutableLiveData<MutableList<String>>(mutableStateListOf())
    val dataList = mutableStateListOf<String>()
    private val context = context
    private val saveCsvFile = SaveCsvFileWriter()
    val label = mutableStateOf("")
    val csvDataList = mutableListOf<Array<String>>()
//    var label by remember { mutableStateOf("") }

    init {
        // Request WRITE_EXTERNAL_STORAGE permission
        val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val requestCode = 123 // You can use any unique code
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context as Activity, arrayOf(permission), requestCode)
        }
    }
//    val bluetoothStatus = MutableLiveData<Boolean>()
//    val preferenceDataStore = PreferenceDataStore.getInstance(context)

//    fun getAllData(): Map<Preferences.Key<*>, Any>{
//        viewModelScope.launch {
//            val allData = preferenceDataStore.getAllData()
//            for ((key, value) in allData) {
//                println("Key: $key, Value: $value")
//            }
//        }
//    }

    fun saveDataCsv(){
        viewModelScope.launch {
            saveCsvFile.saveToCsv(dataList, context, label.value)
        }
    }

    fun readDataCsv(){
        val externalStorageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val csvFile = File(externalStorageDir, "data.csv")
        val data = saveCsvFile.readCsvFile(csvFile)
        // Print the returned array (just for demonstration)
        data.forEach { row ->
//            println(row.joinToString(", "))
            println("data: ${row.contentToString()}")
        }
        csvDataList.clear()  // Clear existing data
        csvDataList.addAll(data)  // Add new data
    }

    fun addDevice(deviceName: String) {
        if (items.value?.contains(deviceName) == false) {
            items.value?.add(deviceName)
        }
    }

    fun addData(data: String) {
        dataList.add(data)
    }

//    fun saveTitle(key: String){
//        viewModelScope.launch {
//            preferenceDataStore.saveTitle(key)
//        }
//    }

//    fun saveData(key: String, value: String){
//        viewModelScope.launch {
//            preferenceDataStore.saveData(key, value)
//        }
//    }

//    fun showData(){
//        viewModelScope.launch {
//            preferenceDataStore.user.collect{
//                println("Thread name {${Thread.currentThread().name}}: collect start")
//                println(it)
//            }
//        }
//    }

//    fun setBluetoothStatus(status: Boolean) {
//        bluetoothStatus.value = status
//    }
}

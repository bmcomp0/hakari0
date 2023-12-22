package com.example.scalesseparatefileble.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import java.math.BigInteger
import java.security.MessageDigest

class BluetoothUtilities {
    companion object {
        fun md5(input: String): String {
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
        }

        fun bleStateMessageChange(state: Int){
            when(state){
                0 -> bleStateMessage.value = ""
                1 -> bleStateMessage.value = "BLE初期化完了"
                2 -> bleStateMessage.value = "MyBLEDevice found!"
                3 -> bleStateMessage.value = "GATT取得完了"
                4 -> bleStateMessage.value = "切断しました"
                5 -> bleStateMessage.value = "133"
                6 -> bleStateMessage.value = "BLE通信中"
                7 -> bleStateMessage.value = "ペリフェラル認証完了"
                8 -> bleStateMessage.value = "セントラル認証完了"
            }
        }

        val bleStateMessage: MutableState<String> = mutableStateOf("")
    }
}

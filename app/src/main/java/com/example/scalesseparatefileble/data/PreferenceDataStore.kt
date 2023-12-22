//package com.example.scalesseparatefileble.data
//
//import android.content.Context
//import androidx.datastore.core.DataStore
//import androidx.datastore.preferences.core.Preferences
//import androidx.datastore.preferences.core.edit
//import androidx.datastore.preferences.core.emptyPreferences
//import androidx.datastore.preferences.core.stringPreferencesKey
//import androidx.datastore.preferences.preferencesDataStore
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.catch
//import kotlinx.coroutines.flow.firstOrNull
//import kotlinx.coroutines.flow.map
//import java.io.IOException
//
//class PreferenceDataStore (private val context: Context){
////    val dataNameList = mutableStateListOf<String>()
//
//    // Using a set to store unique user keys
//    private val userKeys = mutableSetOf<String>()
//
//
//    companion object {
//        private var instance: PreferenceDataStore? = null
//
//        private const val PREF_NAME_SUFFIX = "dataPreference"
//        private const val USER_PREFIX = "Data:"
//
//        fun getInstance(context: Context): PreferenceDataStore {
//            return instance ?: synchronized(this) {
//                instance ?: PreferenceDataStore(context).also { instance = it }
//            }
//        }
//    }
//
//    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREF_NAME_SUFFIX)
//
//    suspend fun saveTitle(key: String){
//        val userKey = "${USER_PREFIX}_$key"
//        if(!userKeys.contains(userKey)){
//            userKeys.add(userKey)
//        }
//        context.dataStore.edit { preferences ->
//            preferences[stringPreferencesKey(userKey)] = "data"
//        }
//    }
//
//    suspend fun saveData(key: String, value: String){
//        val userKey = "${USER_PREFIX}_$key"
//
//        context.dataStore.edit { preferences ->
//            preferences[stringPreferencesKey(userKey)] = preferences[stringPreferencesKey(userKey)] + "," + value
//        }
//    }
//
//    suspend fun getAllData(): Map<Preferences.Key<*>, Any> {
//        val preferences = context.dataStore.data.firstOrNull() ?: emptyPreferences()
//        return preferences.asMap()
//    }
//
////    suspend fun getAllData(): List<User> {
////        val preferences = context.dataStore.data.firstOrNull() ?: emptyPreferences()
////        return userKeys.map { userKey ->
////            User(
////                label = userKey,
////                weight = preferences[stringPreferencesKey(userKey)] ?: ""
////            )
////        }
////    }
//
//    val user: Flow<List<User>> = context.dataStore.data
//        .catch { exception ->
//            if (exception is IOException) {
//                emit(emptyPreferences())
//            } else {
//                throw exception
//            }
//        }
//        .map { preferences ->
//            // Map each user key to a User object
//            userKeys.map { userKey ->
//                User(
//                    label = userKey,
//                    weight = preferences[stringPreferencesKey(userKey)] ?: ""
//                )
//            }
//        }
//
//
//    data class User(val label: String, val weight: String)
//}
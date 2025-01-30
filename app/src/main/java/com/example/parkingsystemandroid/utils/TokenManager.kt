@file:Suppress("DEPRECATION")

package com.example.parkingsystemandroid.utils

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

object TokenManager {
    private const val PREFS_FILENAME = "secure_prefs"
    private const val TOKEN_KEY = "TOKEN_KEY"
    private const val KEY_USER_ID = "user_id"


    private lateinit var prefs: EncryptedSharedPreferences

    fun init(context: Context) {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        prefs = EncryptedSharedPreferences.create(
            PREFS_FILENAME,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ) as EncryptedSharedPreferences
    }
    fun saveUserId(userId: Int) {
        prefs.edit().putInt(KEY_USER_ID, userId).apply()

    }

    fun getUserId(): Int {
        return prefs.getInt(KEY_USER_ID, -1)

    }
    var token: String?
        get() = prefs.getString(TOKEN_KEY, null)
        set(value) = prefs.edit().putString(TOKEN_KEY, value).apply()

    fun clearToken() {
        prefs.edit().remove(TOKEN_KEY).apply()
    }
}
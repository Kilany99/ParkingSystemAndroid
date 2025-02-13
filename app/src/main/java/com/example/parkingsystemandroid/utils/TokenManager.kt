@file:Suppress("DEPRECATION")

package com.example.parkingsystemandroid.utils

import android.content.Context
import android.util.Base64
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.parkingsystemandroid.ParkingSystemApplication
import org.json.JSONObject

object TokenManager {
    private const val PREFS_FILENAME = "secure_prefs"
    private const val TOKEN_KEY = "TOKEN_KEY"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_USER_EMAIL = "user_email"

    private val context: Context
        get() = ParkingSystemApplication.appContext

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

    fun getUserName(): String? {
        val prefs = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_USER_NAME, null)
    }


    fun getUserEmail(): String? {
        val prefs = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_USER_EMAIL, null)
    }
    fun saveUserName(userName: String) {
        val prefs = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_USER_NAME, userName).apply()
    }

    fun saveUserEmail(userEmail: String) {
        val prefs = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_USER_EMAIL, userEmail).apply()
    }

    // Clear all user data on logout
    fun clearUserData() {
        val prefs = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }
    var token: String?
        get() = prefs.getString(TOKEN_KEY, null)
        set(value) = prefs.edit().putString(TOKEN_KEY, value).apply()
    var userRole: String? = null
        get() {
            token?.let {
                val parts = it.split(".") // JWT has 3 parts: Header, Payload, Signature
                if (parts.size > 1) {
                    val payload = String(Base64.decode(parts[1], Base64.URL_SAFE))
                    val json = JSONObject(payload)
                    return json.optString("role", null) // Extract role from JWT
                }
            }
            return null
        }
    fun clearToken() {
        prefs.edit().remove(TOKEN_KEY).apply()
    }

}
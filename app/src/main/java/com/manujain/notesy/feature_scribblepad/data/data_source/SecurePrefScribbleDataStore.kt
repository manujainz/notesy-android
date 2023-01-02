package com.manujain.notesy.feature_scribblepad.data.data_source

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.manujain.notesy.feature_scribblepad.domain.model.Scribble

class SecurePrefScribbleDataStore(context: Context) : ScribbleDataStore {
    private val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
    private val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

    private val sharedPrefsFile: String = "scribblepad_pref"
    private val sharedPrefsScribbleKey = "scribble_data"

    private val sharedPreferences by lazy {
        EncryptedSharedPreferences.create(
            sharedPrefsFile,
            mainKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override suspend fun getScribble(): Scribble {
        val data = sharedPreferences.getString(sharedPrefsScribbleKey, null)
        return if (data == null) Scribble("") else Scribble(data)
    }

    override suspend fun storeScribble(scribble: Scribble) {
        with(sharedPreferences.edit()) {
            this.putString(sharedPrefsScribbleKey, scribble.data)
            this.apply()
        }
    }

    override suspend fun deleteScribble() {
        with(sharedPreferences.edit()) {
            this.remove(sharedPrefsScribbleKey)
            this.apply()
        }
    }
}

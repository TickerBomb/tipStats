package domain

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface AppPreferences {
    suspend fun setFid(fid: String): Preferences
    fun getFid(): Flow<String>
}

internal class AppPreferencesImpl(
    private val dataStore: DataStore<Preferences>
) : AppPreferences {
    private val fidKey = stringPreferencesKey("PREF_FID")
    override suspend fun setFid(fid: String): Preferences {
        return dataStore.edit { mutablePreferences ->
            mutablePreferences[fidKey] = fid
        }
    }

    override fun getFid(): Flow<String> {
        return dataStore.data.map { it[fidKey] ?: "" }
    }
}
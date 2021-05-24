package io.github.bradpatras.todometer.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.bradpatras.todometer.core.data.AppPreferencesDataSource
import io.github.bradpatras.todometer.core.domain.AppPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")
private val collapsedSectionIdsPrefsKey: Preferences.Key<Set<String>> = stringSetPreferencesKey("collapsedSectionIds")

class LocalAppPreferencesDataSource @Inject constructor(@ApplicationContext val context: Context): AppPreferencesDataSource {
    override fun getPreferencesFlow(): Flow<AppPreferences> = context.dataStore.data
        .catch {
            emit(emptyPreferences())
        }
        .map { preferences ->
            val stringIds = preferences[collapsedSectionIdsPrefsKey]
            val ids: List<Int> = stringIds?.map { it.toInt() } ?: emptyList()
            return@map AppPreferences(ids)
    }
}
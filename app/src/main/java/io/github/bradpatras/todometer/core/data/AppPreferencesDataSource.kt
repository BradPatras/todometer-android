package io.github.bradpatras.todometer.core.data

import io.github.bradpatras.todometer.core.domain.AppPreferences
import kotlinx.coroutines.flow.Flow

interface AppPreferencesDataSource {
     fun getPreferencesFlow(): Flow<AppPreferences>
}
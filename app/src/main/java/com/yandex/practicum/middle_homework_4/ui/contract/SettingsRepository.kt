package com.yandex.practicum.middle_homework_4.ui.contract

import com.yandex.practicum.middle_homework_4.data.setting_repository.IntervalSettings
import kotlinx.coroutines.flow.StateFlow

interface SettingsRepository {
    val state: StateFlow<IntervalSettings>
    suspend fun saveSetting(periodic: Long, delayed: Long)
    suspend fun readSetting()
}
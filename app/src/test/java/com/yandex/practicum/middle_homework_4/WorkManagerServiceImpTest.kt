package com.yandex.practicum.middle_homework_4

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.work.Configuration
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import com.yandex.practicum.middle_homework_4.data.setting_repository.IntervalSettings
import com.yandex.practicum.middle_homework_4.data.sync.WorkManagerServiceImp
import com.yandex.practicum.middle_homework_4.ui.contract.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class WorkManagerServiceImpTest {

    private lateinit var context: Context
    private lateinit var settingsRepository: SettingsRepository

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        settingsRepository = mock(SettingsRepository::class.java)
        val config = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .setExecutor(SynchronousExecutor())
            .build()
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
    }

    @Test
    fun testLaunchRefreshWork() = runBlocking {
        val settingsFlow = MutableStateFlow(IntervalSettings(15, 5))
        `when`(settingsRepository.state).thenReturn(settingsFlow)

        val workManagerService = WorkManagerServiceImp(context, settingsRepository)
        workManagerService.launchRefreshWork()

        val workManager = WorkManager.getInstance(context)
        val workInfo = workManager.getWorkInfosByTag(WorkManagerServiceImp.REFRESH_WORK_NAME).get()

        assertNotNull(workInfo)
        assertEquals(WorkInfo.State.ENQUEUED, workInfo.first().state)
    }

    @Test
    fun testCancelRefreshWork() = runBlocking {
        val settingsFlow = MutableStateFlow(IntervalSettings(15, 5))
        `when`(settingsRepository.state).thenReturn(settingsFlow)

        val workManagerService = WorkManagerServiceImp(context, settingsRepository)
        workManagerService.launchRefreshWork()
        workManagerService.cancelRefreshWork()

        val workManager = WorkManager.getInstance(context)
        val workInfo =
            workManager.getWorkInfosByTag(WorkManagerServiceImp.REFRESH_WORK_NAME).get()
        assertEquals(WorkInfo.State.CANCELLED, workInfo[0].state)
    }
}
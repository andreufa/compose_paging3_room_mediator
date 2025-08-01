package com.yandex.practicum.middle_homework_4.data.setting_repository

data class IntervalSettings(
    val periodic: Long,
    val delayed: Long
) {

    companion object {
        const val DEFAULT_REFRESH_PERIOD: Long = 15
        const val FIST_LAUNCH_DELAY: Long = 10
        val initial: IntervalSettings = IntervalSettings(
            periodic = DEFAULT_REFRESH_PERIOD,
            delayed = FIST_LAUNCH_DELAY
        )
    }
}
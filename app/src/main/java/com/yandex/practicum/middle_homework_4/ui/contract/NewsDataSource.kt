package com.yandex.practicum.middle_homework_4.ui.contract

import com.yandex.practicum.middle_homework_4.data.source.NewsResponse

interface NewsDataSource {
    fun fetchData(page: Int): NewsResponse
}
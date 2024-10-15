package com.example.movieapplicationcompose.paging

interface Pagination<Key, Item> {
    suspend fun loadNextPage()
    fun reset()
}
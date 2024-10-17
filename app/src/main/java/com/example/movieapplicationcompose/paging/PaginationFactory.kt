package com.example.movieapplicationcompose.paging

import com.example.movieapplicationcompose.utils.Result

class PaginationFactory<Key, Item>(
    private val initialPage: Key,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextPage: Key) -> Result<Item>,
    private inline val getNextKey: suspend (Item) -> Key,
    private inline val onError: suspend (Throwable?) -> Unit,
    private inline val onSuccess: suspend (item: Item, newKey: Key) -> Unit,
) : Pagination<Key, Item> {
    private var currentKey = initialPage
    private var isMakingRequest = false

    override suspend fun loadNextPage() {
        if (isMakingRequest) return

        isMakingRequest = true
        onLoadUpdated(true)

        try {
            val result = onRequest(currentKey)

            when (result) {
                is Result.Success -> {
                    isMakingRequest = false
                    val items = result.data
                    currentKey = getNextKey(items)!!
                    onSuccess(items, currentKey)
                }

                is Result.Error -> {
                    isMakingRequest = false
                    onError(result.exception)
                }
            }
            onLoadUpdated(false)
        } catch (e: Exception) {
            onError(e)
        } finally {
            isMakingRequest = false
            onLoadUpdated(false)
        }
    }

    override fun reset() {
        currentKey = initialPage
    }
}
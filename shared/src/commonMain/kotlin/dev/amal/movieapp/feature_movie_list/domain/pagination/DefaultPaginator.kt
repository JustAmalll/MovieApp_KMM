package dev.amal.movieapp.feature_movie_list.domain.pagination

import dev.amal.movieapp.core.utils.Resource

class DefaultPaginator<Key, Item>(
    private val initialKey: Key,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: Key) -> Resource<List<Item>>,
    private inline val getNextKey: suspend () -> Key,
    private inline val onError: suspend (String?) -> Unit,
    private inline val onSuccess: suspend (items: List<Item>, newKey: Key) -> Unit
) : Paginator<Key, Item> {

    private var currentKey = initialKey
    private var isMakingRequest = false

    override suspend fun loadNextItems() {
        if (isMakingRequest) return
        isMakingRequest = true
        onLoadUpdated(true)

        when (val result = onRequest(currentKey)) {
            is Resource.Success -> result.data?.let { items ->
                currentKey = getNextKey()
                onSuccess(items, currentKey)
            }
            is Resource.Error -> {
                onError(result.message)
            }
        }
        onLoadUpdated(false)
        isMakingRequest = false
    }

    override fun reset() {
        currentKey = initialKey
    }
}
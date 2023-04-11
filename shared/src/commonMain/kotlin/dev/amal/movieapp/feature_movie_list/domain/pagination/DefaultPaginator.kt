package dev.amal.movieapp.feature_movie_list.domain.pagination

import dev.amal.movieapp.feature_favorite_movies.domain.repository.NetworkError
import dev.amal.movieapp.feature_favorite_movies.domain.repository.NetworkException

class DefaultPaginator<Key, Item>(
    private val initialKey: Key,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: Key) -> List<Item>,
    private inline val getNextKey: suspend () -> Key,
    private inline val onError: suspend (NetworkError?) -> Unit,
    private inline val onSuccess: suspend (items: List<Item>, newKey: Key) -> Unit
) : Paginator<Key, Item> {

    private var currentKey = initialKey
    private var isMakingRequest = false

    override suspend fun loadNextItems() {
        if (isMakingRequest) return
        isMakingRequest = true
        onLoadUpdated(true)

        try {
            val result = onRequest(currentKey)
            currentKey = getNextKey()
            onSuccess(result, currentKey)
        } catch (exception: NetworkException) {
            onError(exception.error)
        }
        onLoadUpdated(false)
        isMakingRequest = false
    }

    override fun reset() {
        currentKey = initialKey
    }
}
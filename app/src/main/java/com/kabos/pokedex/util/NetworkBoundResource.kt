package com.kabos.pokedex.util

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.coroutineScope

abstract class NetworkBoundResource<ResultType, RequestType> () {
    //LiveData that represents the resource, implemented in the base class
    var result: Resource<ResultType>? = null

    abstract suspend fun queryFromDb(): ResultType

    abstract suspend fun fetchFromNetwork(): RequestType

    abstract suspend fun saveFetchResult(fetchFromNetwork: RequestType): (RequestType) -> Unit

    abstract fun shouldFetch(data: ResultType?): Boolean

    abstract fun onFetchFailed(t: Throwable)


    suspend fun execute() = coroutineScope {
        val data = queryFromDb()
        //Loadingってなぜいるの？？？わからん
        //result.postValue(Resource.Loading(data))

        result = if (shouldFetch(data)) {
            try {
                saveFetchResult(fetchFromNetwork())
                Resource.Success(queryFromDb())
            } catch(t: Throwable) {
                onFetchFailed(t)
                Resource.Error(t, queryFromDb())
            }
        } else {
            Resource.Success(data)
        }
    }

}
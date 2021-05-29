package com.kabos.pokedex.util

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.coroutineScope


abstract class NetworkBoundResource<ResultType, RequestType> () {
    //LiveData that represents the resource, implemented in the base class
    val result: MutableLiveData<Resource<ResultType>> = MutableLiveData()

    abstract suspend fun queryFromDb(): ResultType

    abstract suspend fun fetchFromNetwork(): RequestType

    abstract suspend fun saveFetchResult(fetchFromNetwork: RequestType): (RequestType) -> Unit

    abstract fun shouldFetch(data: ResultType?): Boolean

    abstract fun onFetchFailed(t: Throwable)


    suspend fun execute() = coroutineScope {
        val data = queryFromDb()
        //Loadingってなぜいるの？？？わからん
        //result.postValue(Resource.Loading(data))

        if (shouldFetch(data)) {
            try {
                saveFetchResult(fetchFromNetwork())
                result.postValue(Resource.Success(queryFromDb()))
            } catch(t: Throwable) {
                onFetchFailed(t)
                result.postValue(Resource.Error(t, queryFromDb()))
            }
        } else {
            result.postValue(Resource.Success(data))
        }
    }

}
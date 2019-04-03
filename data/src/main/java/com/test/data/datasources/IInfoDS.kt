package com.test.data.datasources

import com.test.domain.model.Info
import io.reactivex.Completable
import io.reactivex.Single

interface IInfoDS {

    fun fetchInfo(): Single<Info> {
        return Single.error(UnsupportedOperationException("This DS implementation does not support fetchInfo() function"))
    }

    fun fetchFromStorage(): Single<Info> {
        return Single.error(UnsupportedOperationException("This DS implementation does not support fetchFromStorage() function"))
    }

    fun dispatchToStorage(info: Info): Completable {
        return Completable.error(UnsupportedOperationException("This DS implementation does not support dispatchToStorage() function"))
    }
}
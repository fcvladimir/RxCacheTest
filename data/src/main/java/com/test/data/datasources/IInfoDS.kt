package com.test.data.datasources

import com.test.domain.model.Info
import io.reactivex.Completable
import io.reactivex.Single

interface IInfoDS {

    fun fetchInfo(): Single<Info> {
        return Single.error(UnsupportedOperationException("This DS implementation does not support fetchCorrMessage() function"))
    }

    fun saveInfo(): Completable {
        return Completable.error(UnsupportedOperationException("This DS implementation does not support modifyCorrMessage() function"))
    }
}
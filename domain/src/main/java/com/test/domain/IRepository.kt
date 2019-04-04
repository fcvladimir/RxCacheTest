package com.test.domain

import com.test.domain.model.Info
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface IRepository<V> {

    fun <P : Get<V>> get(param: P): Single<V>

    fun <P : Update<V>> update(param: P): Completable

    fun <P : Delete> delete(param: P): Completable

    fun <P : Observe> observe(param: P): Observable<V>

    interface Get<V> {

        /**
         * Fetch corr message
         */
        class FetchInfo : Get<Info>
    }

    interface Update<V>

    interface Delete

    interface Observe {

        class FetchInfoSubscriber : Observe
    }

}


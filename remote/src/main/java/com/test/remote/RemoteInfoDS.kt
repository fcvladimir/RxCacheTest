package com.test.remote

import com.test.data.datasources.IInfoDS
import com.test.domain.model.Info
import com.test.remote.server.IRemoteService
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class RemoteInfoDS(
        private val productsService: IRemoteService,
        private val mapper: Mapper
) : IInfoDS {

    override fun fetchInfo(): Single<Info> {
        return productsService.fetchInfo()
                .map {
                    mapper.toDomain(it)
                }
                .subscribeOn(Schedulers.io())
    }
}
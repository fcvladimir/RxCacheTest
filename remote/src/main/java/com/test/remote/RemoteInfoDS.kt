package com.test.remote

import com.test.data.datasources.IInfoDS
import com.test.domain.model.Info
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class RemoteInfoDS(
        private val productsService: IRemoteService,
        private val mapper: Mapper,
        private val endpointResolver: IEndpointResolver
) : IInfoDS {

    override fun fetchInfo(): Single<Info> {
        return productsService.fetchCorrMessage()
                .map {
                    mapper.toDomain(it)
                }
                .subscribeOn(Schedulers.io())
    }
}
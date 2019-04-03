package com.test.remote.server

import com.test.remote.model.InfoResult
import io.reactivex.Single
import kotlin.random.Random

class RemoteServiceImpl : IRemoteService {

    override fun fetchInfo(): Single<InfoResult> {
        return Single.just(InfoResult(Random.nextInt(100)))
    }
}
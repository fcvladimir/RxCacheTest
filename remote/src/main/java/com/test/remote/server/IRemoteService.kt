package com.test.remote.server

import com.test.remote.model.InfoResult
import io.reactivex.Single

interface IRemoteService {

    fun fetchInfo(): Single<InfoResult>

}
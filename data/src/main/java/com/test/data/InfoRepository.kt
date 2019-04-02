package com.test.data

import com.test.data.datasources.IInfoDS
import com.test.domain.IRepository
import com.test.domain.model.Info
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class InfoRepository(
        private val localInfoDs: IInfoDS,
        private val remoteInfoDs: IInfoDS
) : IRepository<Info> {

    override fun <P : IRepository.Get<Info>> get(param: P): Single<Info> {
        return when (param) {
            is IRepository.Get.FetchInfo -> fetchInfo()
            else -> throw UnsupportedOperationException("${param::class.java.name} operation is unsupported")
        }
    }

    override fun <P : IRepository.Update<Info>> update(param: P): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <P : IRepository.Delete> delete(param: P): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <P : IRepository.Observe> observe(param: P): Observable<Info> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun fetchInfo() =
            corrMessageDs.fetchCorrMessage()
                    .onErrorResumeNext(
                            corrMessageDs.fetchCorrMessage("", "")
                    )
}
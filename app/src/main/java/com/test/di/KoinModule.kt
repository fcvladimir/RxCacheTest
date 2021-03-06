package com.test.di

import com.test.data.InfoRepository
import com.test.data.datasources.IInfoDS
import com.test.domain.IRepository
import com.test.domain.info.UcFetchInfo
import com.test.domain.info.UcFetchInfoSubscriber
import com.test.domain.model.Info
import com.test.local.info.LocalInfoDS
import com.test.presentation.mvp.home.IInfoContract
import com.test.presentation.mvp.home.InfoPresenter
import com.test.remote.Mapper
import com.test.remote.RemoteInfoDS
import com.test.remote.server.IRemoteService
import com.test.remote.server.RemoteServiceImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val mainModule = module {

    single { AndroidSchedulers.mainThread() }
    factory { CompositeDisposable() }

    /*
    * Service
    * */
    single<IRemoteService>(name = KOIN_NAME_REMOTE_SERVICE) { RemoteServiceImpl() }

    /*
    * Mappers
    * */
    single { Mapper() }

    /*
    * Info DS
    * */
    single<IInfoDS>(name = KOIN_NAME_DS_INFO_REMOTE) { RemoteInfoDS(get(), get()) }
    single<IInfoDS>(name = KOIN_NAME_DS_INFO_IN_MEMORY) { LocalInfoDS(androidContext()) }

    /*
    * Repositories
    * */
    factory<IRepository<Info>>(name = KOIN_NAME_REPOSITORY_INFO) { InfoRepository(get(name = KOIN_NAME_DS_INFO_REMOTE), get(name = KOIN_NAME_DS_INFO_IN_MEMORY)) }

    /*
    * UseCases
    * */
    factory(name = UcFetchInfo::class.java.name) { UcFetchInfo(get(), get(), get(KOIN_NAME_REPOSITORY_INFO)) }
    factory(name = UcFetchInfoSubscriber::class.java.name) { UcFetchInfoSubscriber(get(), get(), get(KOIN_NAME_REPOSITORY_INFO)) }

    /*
    * Presenters
    * */
    scope<IInfoContract.Presenter>(scopeId = KOIN_KEY_SCOPE_INFO_FRAGMENT) {
        InfoPresenter(get(UcFetchInfo::class.java.name), get(UcFetchInfoSubscriber::class.java.name))
    }
}

enum class ScopeName {
    KOIN_KEY_SCOPE_MAIN_ACTIVITY,

    KOIN_KEY_SCOPE_INFO_FRAGMENT
}

val KOIN_KEY_SCOPE_MAIN_ACTIVITY = ScopeName.KOIN_KEY_SCOPE_MAIN_ACTIVITY.name
val KOIN_KEY_SCOPE_INFO_FRAGMENT = ScopeName.KOIN_KEY_SCOPE_INFO_FRAGMENT.name

val KOIN_NAME_REPOSITORY_INFO = InfoRepository::class.java.name

val KOIN_NAME_DS_INFO_REMOTE = RemoteInfoDS::class.java.name
val KOIN_NAME_DS_INFO_IN_MEMORY = LocalInfoDS::class.java.name

val KOIN_NAME_REMOTE_SERVICE = RemoteServiceImpl::class.java.name
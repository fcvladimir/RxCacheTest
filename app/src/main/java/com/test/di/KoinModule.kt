package com.test.di

import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module
import java.io.File

val mainModule = module {

    single { AndroidSchedulers.mainThread() }
    factory { CompositeDisposable() }

    single { PrefsHelper(androidApplication()) }
    single { Gson() }

    single { RetrofitServiceFactory(get()) }

    single<IEndpointResolver> { EndpointResolver(get()) }

    /*
    * Remote RetrofitServices
    * */
    single { get<RetrofitServiceFactory>().createService() }

    /*
    * DAO
    * */
    single<ICredentialsDao> { CredentialsDao(get(), get()) }
    single<ILocalFile> { LocalFile(get(), get(), androidContext(), get()) }

    /*
    * Mappers
    * */
    single { Mapper() }

    /*
    * Login DS
    * */
    single<ILoginDS>(name = KOIN_NAME_DS_LOGIN_REMOTE) { RemoteLoginDS(get(), get(), get()) }

    /*
    * Credentials DS
    * */
    single<ICredentialsDS>(name = KOIN_NAME_DS_CREDENTIALS_PREFS) { PrefsCredentialsDS(get()) }

    /*
    * Session DS
    * */
    single<ISessionDS>(name = KOIN_NAME_DS_SESSION_PREFS) { PrefsSessionDS(get()) }

    /*
    * Documents DS
    * */
    single<IDocumentsDS>(name = KOIN_NAME_DS_DOCUMENTS_REMOTE) { RemoteDocumentsDS(get(), get(), get()) }

    /*
    * Resolution DS
    * */
    single<IExternalResolutionDS>(name = KOIN_NAME_DS_RESOLUTION_REMOTE) { RemoteExternalResolutionDS(get(), get(), get()) }

    /*
    * Document Text DS
    * */
    single<IDocumentTextDS>(name = KOIN_NAME_DS_DOCUMENT_TEXT_REMOTE) { RemoteDocumentTextDS(get(), get(), get()) }

    /*
    * Document Archive DS
    * */
    single<IDocumentArchiveDS>(name = KOIN_NAME_DS_DOCUMENT_ARCHIVE_REMOTE) { RemoteDocumentArchiveDS(get(), get(), get()) }

    /*
    * Document Archive DS
    * */
    single<ICorrMessageDS>(name = KOIN_NAME_DS_CORR_MESSAGE_REMOTE) { RemoteCorrMessageDS(get(), get(), get()) }

    /*
    * Local File DS
    * */
    single<IFileDS>(name = KOIN_NAME_DS_LOCAL_FILE) { LocalFileDS(get()) }

    /*
    * Workers DS
    * */
    single<IWorkersDS>(name = KOIN_NAME_DS_WORKERS) { RemoteWorkersDS(get(), get(), get()) }

    /*
    * Repositories
    * */
    factory<IRepository<CheckLoginModel>>(name = KOIN_NAME_REPOSITORY_LOGIN) { LoginRepository(get(name = KOIN_NAME_DS_LOGIN_REMOTE)) }
    factory<IRepository<Permissions>>(name = KOIN_NAME_REPOSITORY_PERMISSIONS) { PermissionsRepository(get(name = KOIN_NAME_DS_LOGIN_REMOTE)) }
    factory<IRepository<Credentials>>(name = KOIN_NAME_REPOSITORY_CREDENTIALS) { CredentialsRepository(get(name = KOIN_NAME_DS_CREDENTIALS_PREFS)) }
    factory<IRepository<List<DocumentsForSignature>>>(name = KOIN_NAME_REPOSITORY_DOCUMENTS) { DocumentsRepository(get(name = KOIN_NAME_DS_DOCUMENTS_REMOTE)) }
    factory<IRepository<List<DocumentText>>>(name = KOIN_NAME_REPOSITORY_DOCUMENT_TEXT) { DocumentTextRepository(get(name = KOIN_NAME_DS_DOCUMENT_TEXT_REMOTE)) }
    factory<IRepository<List<DocumentArchive>>>(name = KOIN_NAME_REPOSITORY_DOCUMENT_ARCHIVE) { DocumentArchiveRepository(get(name = KOIN_NAME_DS_DOCUMENT_ARCHIVE_REMOTE)) }
    factory<IRepository<List<ExternalResolution>>>(name = KOIN_NAME_REPOSITORY_EXTERNAL_RESOLUTION) { ExternalResolutionRepository(get(name = KOIN_NAME_DS_RESOLUTION_REMOTE)) }
    factory<IRepository<List<Resolution>>>(name = KOIN_NAME_REPOSITORY_RESOLUTION) { ResolutionRepository(get(name = KOIN_NAME_DS_RESOLUTION_REMOTE)) }
    factory<IRepository<Session>>(name = KOIN_NAME_REPOSITORY_SESSION) { SessionRepository(get(name = KOIN_NAME_DS_SESSION_PREFS)) }
    factory<IRepository<List<CorrMessage>>>(name = KOIN_NAME_REPOSITORY_CORR_MESSAGE) { CorrMessageRepository(get(name = KOIN_NAME_DS_CORR_MESSAGE_REMOTE)) }
    factory<IRepository<File>>(name = KOIN_NAME_REPOSITORY_LOCAL_FILE) { LocalFileRepository(get(name = KOIN_NAME_DS_LOCAL_FILE)) }
    factory<IRepository<List<Worker>>>(name = KOIN_NAME_REPOSITORY_WORKERS) { WorkersRepository(get(name = KOIN_NAME_DS_WORKERS)) }

    /*
    * UseCases
    * */
    factory(name = UcCheckLogin::class.java.name) { UcCheckLogin(get(), get(), get(KOIN_NAME_REPOSITORY_LOGIN), get(KOIN_NAME_REPOSITORY_CREDENTIALS), get(KOIN_NAME_REPOSITORY_SESSION)) }
    factory(name = UcLogin::class.java.name) { UcLogin(get(), get(), get(KOIN_NAME_REPOSITORY_LOGIN), get(KOIN_NAME_REPOSITORY_CREDENTIALS), get(KOIN_NAME_REPOSITORY_SESSION)) }
    factory(name = UcLogout::class.java.name) { UcLogout(get(), get(), get(KOIN_NAME_REPOSITORY_LOGIN), get(KOIN_NAME_REPOSITORY_CREDENTIALS), get(KOIN_NAME_REPOSITORY_SESSION)) }
    factory(name = UcCheckLoginSignature::class.java.name) { UcCheckLoginSignature(get(), get(), get(KOIN_NAME_REPOSITORY_LOGIN), get(KOIN_NAME_REPOSITORY_CREDENTIALS), get(KOIN_NAME_REPOSITORY_SESSION)) }
    factory(name = UcChangePassword::class.java.name) { UcChangePassword(get(), get(), get(KOIN_NAME_REPOSITORY_LOGIN), get(KOIN_NAME_REPOSITORY_CREDENTIALS)) }
    factory(name = UcUpdatePassword::class.java.name) { UcUpdatePassword(get(), get(), get(KOIN_NAME_REPOSITORY_LOGIN), get(KOIN_NAME_REPOSITORY_CREDENTIALS)) }
    factory(name = UcCheckAutologin::class.java.name) { UcCheckAutologin(get(), get(), get(KOIN_NAME_REPOSITORY_SESSION)) }
    factory(name = UcCheckPermissions::class.java.name) { UcCheckPermissions(get(), get(), get(KOIN_NAME_REPOSITORY_LOGIN), get(KOIN_NAME_REPOSITORY_PERMISSIONS), get(KOIN_NAME_REPOSITORY_CREDENTIALS)) }
    factory(name = UcFetchDocumentsForSignature::class.java.name) { UcFetchDocumentsForSignature(get(), get(), get(KOIN_NAME_REPOSITORY_DOCUMENTS)) }
    factory(name = UcFetchDocumentText::class.java.name) { UcFetchDocumentText(get(), get(), get(KOIN_NAME_REPOSITORY_DOCUMENT_TEXT)) }
    factory(name = UcFetchDocumentArchive::class.java.name) { UcFetchDocumentArchive(get(), get(), get(KOIN_NAME_REPOSITORY_DOCUMENT_ARCHIVE)) }
    factory(name = UcModifyExternalResolution::class.java.name) { UcModifyExternalResolution(get(), get(), get(KOIN_NAME_REPOSITORY_EXTERNAL_RESOLUTION)) }
    factory(name = UcFetchResolutionAndControl::class.java.name) { UcFetchResolutionAndControl(get(), get(), get(KOIN_NAME_REPOSITORY_RESOLUTION)) }
    factory(name = UcFetchExternalResolution::class.java.name) { UcFetchExternalResolution(get(), get(), get(KOIN_NAME_REPOSITORY_EXTERNAL_RESOLUTION)) }
    factory(name = UcFetchCorrMessage::class.java.name) { UcFetchCorrMessage(get(), get(), get(KOIN_NAME_REPOSITORY_CORR_MESSAGE)) }
    factory(name = UcConvertToArchiveAndSave::class.java.name) { UcConvertToArchiveAndSave(get(), get(), get(KOIN_NAME_REPOSITORY_LOCAL_FILE)) }
    factory(name = UcFetchWorkers::class.java.name) { UcFetchWorkers(get(), get(), get(KOIN_NAME_REPOSITORY_WORKERS)) }
    factory(name = UcModifyNotification::class.java.name) { UcModifyNotification(get(), get(), get(KOIN_NAME_REPOSITORY_CORR_MESSAGE)) }

    /*
    * Routers
    * */
    scope<IAuthActRouter>(scopeId = KOIN_KEY_SCOPE_AUTH_ACTIVITY) { (activity: BaseActivity) -> AuthActRouter(activity) }
    scope<IAuthRouter>(scopeId = KOIN_KEY_SCOPE_AUTH_FRAGMENT) { (activity: BaseActivity) -> AuthRouter(activity) }
    scope<IChangePasswordRouter>(scopeId = KOIN_KEY_SCOPE_SET_PASSWORD_FRAGMENT) { (activity: BaseActivity) -> ChangePasswordRouter(activity) }
    scope<ILoginRouter>(scopeId = KOIN_KEY_SCOPE_LOGIN_FRAGMENT) { (activity: BaseActivity) -> LoginRouter(activity) }
    scope<IMenuRouter>(scopeId = KOIN_KEY_SCOPE_MENU_FRAGMENT) { (activity: BaseActivity) -> MenuRouter(activity) }
    scope<IDocumentChooseRouter>(scopeId = KOIN_KEY_SCOPE_DOCUMENT_CHOOSE_FRAGMENT) { (activity: BaseActivity) -> DocumentChooseRouter(activity) }
    scope<IAppealChooseRouter>(scopeId = KOIN_KEY_SCOPE_APPEAL_CHOOSE_FRAGMENT) { (activity: BaseActivity) -> AppealChooseRouter(activity) }
    scope<IAppealResultRouter>(scopeId = KOIN_KEY_SCOPE_APPEAL_RESULT_FRAGMENT) { (activity: BaseActivity) -> AppealResultRouter(activity) }
    scope<ISignatureLoginRouter>(scopeId = KOIN_KEY_SCOPE_SIGNATURE_LOGIN_FRAGMENT) { (activity: BaseActivity) -> SignatureLoginRouter(activity) }
    scope<IDocumentsForSignatureRouter>(scopeId = KOIN_KEY_SCOPE_DOCUMENTS_FOR_SIGNATURE_FRAGMENT) { (activity: BaseActivity) -> DocumentsForSignatureRouter(activity) }
    scope<ISingleDocumentRouter>(scopeId = KOIN_KEY_SCOPE_SINGLE_DOCUMENT_FRAGMENT) { (activity: BaseActivity) -> SingleDocumentRouter(activity) }
    scope<IMultipleDocumentRouter>(scopeId = KOIN_KEY_SCOPE_MULTIPLE_DOCUMENT_FRAGMENT) { (activity: BaseActivity) -> MultipleDocumentRouter(activity) }
    scope<ICorrMessageRouter>(scopeId = KOIN_KEY_SCOPE_CORR_MESSAGE_FRAGMENT) { (activity: BaseActivity) -> CorrMessageRouter(activity) }
    scope<IResolutionRouter>(scopeId = KOIN_KEY_SCOPE_RESOLUTION_FRAGMENT) { (activity: BaseActivity) -> ResolutionRouter(activity) }
    scope<INotificationRouter>(scopeId = KOIN_KEY_SCOPE_NOTIFICATION_FRAGMENT) { (activity: BaseActivity) -> NotificationRouter(activity) }
    scope<ISettingsRouter>(scopeId = KOIN_KEY_SCOPE_SETTINGS_FRAGMENT) { (activity: BaseActivity) -> SettingsRouter(activity) }

    /*
    * Presenters
    * */
    scope<IAuthActContract.Presenter>(scopeId = KOIN_KEY_SCOPE_AUTH_ACTIVITY) {
        LoginActPresenter(get(UcCheckAutologin::class.java.name))
    }
    scope<IAuthContract.Presenter>(scopeId = KOIN_KEY_SCOPE_AUTH_FRAGMENT) {
        AuthPresenter(get(UcCheckPermissions::class.java.name))
    }
    scope<IChangePasswordContract.Presenter>(scopeId = KOIN_KEY_SCOPE_SET_PASSWORD_FRAGMENT) {
        ChangePasswordPresenter(get(UcChangePassword::class.java.name))
    }
    scope<ILoginContract.Presenter>(scopeId = KOIN_KEY_SCOPE_LOGIN_FRAGMENT) {
        LoginPresenter(get(UcLogin::class.java.name))
    }
    scope<IMenuContract.Presenter>(scopeId = KOIN_KEY_SCOPE_MENU_FRAGMENT) {
        MenuPresenter()
    }
    scope<IDocumentChooseContract.Presenter>(scopeId = KOIN_KEY_SCOPE_DOCUMENT_CHOOSE_FRAGMENT) {
        DocumentChoosePresenter()
    }
    scope<IAppealChooseContract.Presenter>(scopeId = KOIN_KEY_SCOPE_APPEAL_CHOOSE_FRAGMENT) {
        AppealChoosePresenter()
    }
    scope<IAppealResultContract.Presenter>(scopeId = KOIN_KEY_SCOPE_APPEAL_RESULT_FRAGMENT) {
        AppealResultPresenter()
    }
    scope<ISignatureLoginContract.Presenter>(scopeId = KOIN_KEY_SCOPE_SIGNATURE_LOGIN_FRAGMENT) {
        SignatureLoginPresenter(get(UcCheckLoginSignature::class.java.name))
    }
    scope<IDocumentsForSignatureContract.Presenter>(scopeId = KOIN_KEY_SCOPE_DOCUMENTS_FOR_SIGNATURE_FRAGMENT) {
        DocumentsForSignaturePresenter(get(UcFetchDocumentsForSignature::class.java.name), get(UcFetchDocumentText::class.java.name))
    }
    scope<ISingleDocumentContract.Presenter>(scopeId = KOIN_KEY_SCOPE_SINGLE_DOCUMENT_FRAGMENT) {
        SingleDocumentPresenter(get(UcModifyExternalResolution::class.java.name),
                                get(UcFetchExternalResolution::class.java.name),
                                get(UcFetchDocumentArchive::class.java.name),
                                get(UcConvertToArchiveAndSave::class.java.name))
    }
    scope<IMultipleDocumentContract.Presenter>(scopeId = KOIN_KEY_SCOPE_MULTIPLE_DOCUMENT_FRAGMENT) {
        MultipleDocumentPresenter(get(UcModifyExternalResolution::class.java.name),
                                  get(UcFetchExternalResolution::class.java.name),
                                  get(UcFetchDocumentArchive::class.java.name))
    }
    scope<ICorrMessageContract.Presenter>(scopeId = KOIN_KEY_SCOPE_CORR_MESSAGE_FRAGMENT) {
        CorrMessagePresenter(get(UcFetchCorrMessage::class.java.name))
    }
    scope<IResolutionContract.Presenter>(scopeId = KOIN_KEY_SCOPE_RESOLUTION_FRAGMENT) {
        ResolutionPresenter(get(UcFetchResolutionAndControl::class.java.name))
    }
    scope<INotificationContract.Presenter>(scopeId = KOIN_KEY_SCOPE_NOTIFICATION_FRAGMENT) {
        NotificationPresenter(get(UcFetchWorkers::class.java.name), get(UcModifyNotification::class.java.name))
    }
    scope<ISettingsContract.Presenter>(scopeId = KOIN_KEY_SCOPE_SETTINGS_FRAGMENT) {
        SettingsPresenter(get(UcLogout::class.java.name), get(UcUpdatePassword::class.java.name))
    }
}

enum class ScopeName {
    KOIN_KEY_SCOPE_MAIN_ACTIVITY,

    KOIN_KEY_SCOPE_HOME_FRAGMENT
}

val KOIN_KEY_SCOPE_MAIN_ACTIVITY = ScopeName.KOIN_KEY_SCOPE_MAIN_ACTIVITY.name
val KOIN_KEY_SCOPE_HOME_FRAGMENT = ScopeName.KOIN_KEY_SCOPE_HOME_FRAGMENT.name

val KOIN_NAME_REPOSITORY_LOGIN = HomeRepository::class.java.name

val KOIN_NAME_DS_LOGIN_REMOTE = RemoteLoginDS::class.java.name
val KOIN_NAME_DS_DOCUMENTS_REMOTE = RemoteDocumentsDS::class.java.name
val KOIN_NAME_DS_DOCUMENT_TEXT_REMOTE = RemoteDocumentTextDS::class.java.name
val KOIN_NAME_DS_DOCUMENT_ARCHIVE_REMOTE = RemoteDocumentArchiveDS::class.java.name
val KOIN_NAME_DS_RESOLUTION_REMOTE = RemoteExternalResolutionDS::class.java.name
val KOIN_NAME_DS_CREDENTIALS_PREFS = PrefsCredentialsDS::class.java.name
val KOIN_NAME_DS_SESSION_PREFS = PrefsSessionDS::class.java.name
val KOIN_NAME_DS_CORR_MESSAGE_REMOTE = ICorrMessageDS::class.java.name
val KOIN_NAME_DS_LOCAL_FILE = ILocalFile::class.java.name
val KOIN_NAME_DS_WORKERS = IWorkersDS::class.java.name
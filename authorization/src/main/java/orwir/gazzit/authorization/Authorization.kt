package orwir.gazzit.authorization

import android.net.Uri
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import orwir.gazzit.authorization.model.Token
import orwir.gazzit.common.AuthorizationInterceptor
import retrofit2.Retrofit

@ExperimentalCoroutinesApi
val authorizationModule = module {

    single<AuthorizationService> { get<Retrofit>().create(AuthorizationService::class.java) }

    single { AuthorizationRepository(get()) }

    single<TokenHolder> { get<AuthorizationRepository>()::validToken }

    single<RequestToken> { get<AuthorizationRepository>()::startAuthRequest }

    single<CompleteTokenRequest> { get<AuthorizationRepository>()::completeAuthRequest }

    single<AuthorizationInterceptor> { RedditAuthorizationInterceptor(get()) }

    viewModel { AuthorizationViewModel(get()) }

}

typealias TokenHolder = () -> Token
typealias RequestToken = () -> Flow<Step>
typealias CompleteTokenRequest = suspend (uri: Uri) -> Unit

internal const val CLIENT_ID = "we9xZjW_b19qKQ"
internal const val CREDENTIALS_B64 = "d2U5eFpqV19iMTlxS1E6"
internal const val AUTHORITY = "oauth"
internal const val REDIRECT_URI = "gazzit://$AUTHORITY"
internal const val GRANT_TYPE = "authorization_code"
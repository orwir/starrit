package orwir.gazzit.profile

import org.koin.core.KoinComponent
import org.koin.core.inject

class ProfileRepository : KoinComponent {

    private val service: ProfileService by inject()

    suspend fun profile(): Profile = service.me()

}
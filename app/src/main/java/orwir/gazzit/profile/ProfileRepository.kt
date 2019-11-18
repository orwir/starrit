package orwir.gazzit.profile

import org.koin.core.KoinComponent
import org.koin.core.inject
import orwir.gazzit.model.Profile

class ProfileRepository : KoinComponent {

    private val service: ProfileService by inject()

    suspend fun profile(): Profile = service.me()

}
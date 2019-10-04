package orwir.gazzit.authorization

import android.net.Uri
import androidx.lifecycle.LiveData

interface AuthorizationRepository {

    fun build(): LiveData<Uri>

    fun verify(): LiveData<String>

    fun refresh(): LiveData<String>

    val code: String

}

class BaseAuthorizationRepository : AuthorizationRepository {

    override var code: String = ""
        private set

    override fun build(): LiveData<Uri> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun verify(): LiveData<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refresh(): LiveData<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
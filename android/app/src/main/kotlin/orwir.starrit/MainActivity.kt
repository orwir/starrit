package orwir.starrit

import android.content.Intent
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugins.GeneratedPluginRegistrant
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.StringCodec

class MainActivity: FlutterActivity() {
    lateinit var linksChannel: BasicMessageChannel<String>

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine)
        linksChannel = BasicMessageChannel(flutterEngine.dartExecutor, "starrit/links", StringCodec.INSTANCE)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        linksChannel.send(intent.dataString)
    }
}

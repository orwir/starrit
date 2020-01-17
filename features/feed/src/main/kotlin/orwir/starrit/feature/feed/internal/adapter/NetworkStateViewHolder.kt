package orwir.starrit.feature.feed.internal.adapter

import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import orwir.starrit.core.model.NetworkState
import orwir.starrit.feature.feed.BuildConfig
import orwir.starrit.feature.feed.R
import orwir.starrit.feature.feed.databinding.ViewNetworkStateBinding

internal class NetworkStateViewHolder(
    private val binding: ViewNetworkStateBinding,
    private val onRetry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(networkState: NetworkState?) {
        binding.viewModel = NetworkStateViewModel(networkState, itemView.resources) { onRetry() }
    }

}

internal class NetworkStateViewModel(
    networkState: NetworkState?,
    resources: Resources,
    clickHandler: (View) -> Unit
) {
    val loading = networkState is NetworkState.Loading
    val failed = networkState is NetworkState.Failure
    val errorMessage: CharSequence =
        if (networkState is NetworkState.Failure) {
            networkState.error
                .message?.takeIf { it.isNotBlank() && BuildConfig.DEBUG }
                ?: resources.getText(R.string.posts_loading_error)
        } else {
            ""
        }
    val retry = View.OnClickListener { clickHandler(it) }
}
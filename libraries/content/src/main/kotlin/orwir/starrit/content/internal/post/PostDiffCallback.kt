package orwir.starrit.content.internal.post

import androidx.recyclerview.widget.DiffUtil
import orwir.starrit.content.post.Post

internal object PostDiffCallback : DiffUtil.ItemCallback<Post>() {

    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem == newItem

}
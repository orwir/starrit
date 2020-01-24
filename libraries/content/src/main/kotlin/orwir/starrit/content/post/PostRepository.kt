package orwir.starrit.content.post

import orwir.starrit.content.model.Vote

interface PostRepository {

    fun vote(post: Post, vote: Vote)

    fun comments(post: Post)
    
}
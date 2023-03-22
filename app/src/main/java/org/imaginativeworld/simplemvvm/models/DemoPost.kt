package org.imaginativeworld.simplemvvm.models

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlin.random.Random

@Keep
@JsonClass(generateAdapter = true)
data class DemoPost(
    @Json(name = "id")
    val id: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "body")
    val body: String,
    @Json(name = "user_id")
    val userId: String
) {
    companion object {
        fun getDummies(): List<DemoPost> {
            return listOf(
                DemoPost(
                    "${Random(0).nextInt()}",
                    "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                    "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto",
                    "${Random(0).nextInt()}"
                ),
                DemoPost(
                    "${Random(0).nextInt()}",
                    "qui est esse",
                    "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla",
                    "${Random(0).nextInt()}"
                ),
                DemoPost(
                    "${Random(0).nextInt()}",
                    "eum et est occaecati",
                    "ullam et saepe reiciendis voluptatem adipisci\nsit amet autem assumenda provident rerum culpa\nquis hic commodi nesciunt rem tenetur doloremque ipsam iure\nquis sunt voluptatem rerum illo velit",
                    "${Random(0).nextInt()}"
                ),
                DemoPost(
                    "${Random(0).nextInt()}",
                    "nesciunt quas odio",
                    "repudiandae veniam quaerat sunt sed\nalias aut fugiat sit autem sed est\nvoluptatem omnis possimus esse voluptatibus quis\nest aut tenetur dolor neque",
                    "${Random(0).nextInt()}"
                )
            )
        }
    }
}

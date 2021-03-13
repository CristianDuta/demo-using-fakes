package crdu.demo.fixtures;

import crdu.demo.entities.Comment
import crdu.demo.handlers.CommentsActivityReport

object CommentsActivityReportFixture {
    val commentsActivityReport = CommentsActivityReport(
        total = 25,
        comments = generateComments(1)
                + generateComments(2)
                + generateComments(3)
                + generateComments(4)
                + generateComments(5)
    )

    private fun generateComments(postId: Int): List<Comment> = listOf(
        Comment(
            postId = postId,
            id = 1,
            name = "id labore ex et quam laborum",
            email = "Eliseo@gardner.biz",
            body = "laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium"
        ),
        Comment(
            postId = postId,
            id = 2,
            name = "quo vero reiciendis velit similique earum",
            email = "Jayne_Kuhic@sydney.com",
            body = "est natus enim nihil est dolore omnis voluptatem numquam\net omnis occaecati quod ullam at\nvoluptatem error expedita pariatur\nnihil sint nostrum voluptatem reiciendis et"
        ),
        Comment(
            postId = postId,
            id = 3,
            name = "odio adipisci rerum aut animi",
            email = "Nikita@garfield.biz",
            body = "quia molestiae reprehenderit quasi aspernatur\naut expedita occaecati aliquam eveniet laudantium\nomnis quibusdam delectus saepe quia accusamus maiores nam est\ncum et ducimus et vero voluptates excepturi deleniti ratione"
        ),
        Comment(
            postId = postId,
            id = 4,
            name = "alias odio sit",
            email = "Lew@alysha.tv",
            body = "non et atque\noccaecati deserunt quas accusantium unde odit nobis qui voluptatem\nquia voluptas consequuntur itaque dolor\net qui rerum deleniti ut occaecati"
        ),
        Comment(
            postId = postId,
            id = 5,
            name = "vero eaque aliquid doloribus et culpa",
            email = "Hayden@althea.biz",
            body = "harum non quasi et ratione\ntempore iure ex voluptates in ratione\nharum architecto fugit inventore cupiditate\nvoluptates magni quo et"
        )
    )
}

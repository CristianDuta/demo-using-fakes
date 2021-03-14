package crdu.demo.fixtures

import crdu.demo.entities.Album

class AlbumsFixture(userId: Int) {
    val albums = listOf(
        Album(
            userId = userId,
            id = 1,
            title = "quidem molestiae enim"
        ),
        Album(
            userId = userId,
            id = 2,
            title = "sunt qui excepturi placeat culpa"
        ),
        Album(
            userId = userId,
            id = 3,
            title = "omnis laborum odio"
        ),
        Album(
            userId = userId,
            id = 4,
            title = "non esse culpa molestiae omnis sed optio"
        ),
        Album(
            userId = userId,
            id = 5,
            title = "eaque aut omnis a"
        )
    )
}
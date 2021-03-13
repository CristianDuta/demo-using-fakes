package crdu.demo.fixtures

import crdu.demo.entities.Photo
import crdu.demo.handlers.PhotosActivityReport

object PhotosActivityReportFixture {
    val photosActivityReport = PhotosActivityReport(
        total = 25,
        photos = generatePhotos(1)
                + generatePhotos(2)
                + generatePhotos(3)
                + generatePhotos(4)
                + generatePhotos(5)
    )

    private fun generatePhotos(albumId: Int): List<Photo> = listOf(
        Photo(
            albumId = albumId,
            id = 1,
            title = "accusamus beatae ad facilis cum similique qui sunt",
            url = "https://via.placeholder.com/600/92c952",
            thumbnailUrl = "https://via.placeholder.com/150/92c952"
        ),
        Photo(
            albumId = albumId,
            id = 2,
            title = "reprehenderit est deserunt velit ipsam",
            url = "https://via.placeholder.com/600/771796",
            thumbnailUrl = "https://via.placeholder.com/150/771796"
        ),
        Photo(
            albumId = albumId,
            id = 3,
            title = "officia porro iure quia iusto qui ipsa ut modi",
            url = "https://via.placeholder.com/600/24f355",
            thumbnailUrl = "https://via.placeholder.com/150/24f355"
        ),
        Photo(
            albumId = albumId,
            id = 4,
            title = "culpa odio esse rerum omnis laboriosam voluptate repudiandae",
            url = "https://via.placeholder.com/600/d32776",
            thumbnailUrl = "https://via.placeholder.com/150/d32776"
        ),
        Photo(
            albumId = albumId,
            id = 5,
            title = "natus nisi omnis corporis facere molestiae rerum in",
            url = "https://via.placeholder.com/600/f66b97",
            thumbnailUrl = "https://via.placeholder.com/150/f66b97"
        )
    )
}

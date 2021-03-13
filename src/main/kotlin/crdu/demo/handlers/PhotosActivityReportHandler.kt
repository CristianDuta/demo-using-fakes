package crdu.demo.handlers

import crdu.demo.entities.Album
import crdu.demo.entities.Photo
import crdu.demo.events.EventLogger
import crdu.demo.repositories.AlbumRepository
import crdu.demo.repositories.PhotoRepository
import org.http4k.core.*
import org.http4k.format.Jackson.auto
import org.http4k.lens.Path
import org.http4k.lens.int

data class PhotosActivityReport(
    val total: Int,
    val photos: List<Photo>
)

fun photosActivityReportHandler(
    eventLogger: EventLogger,
    albumRepository: AlbumRepository,
    photoRepository: PhotoRepository
): HttpHandler {
    return {
        val userId = Path.int().of("userId")(it)

        val photos: MutableList<Photo> = mutableListOf()
        val albums: List<Album> = albumRepository.findAllForUser(userId)

        for (album in albums) {
            photos.addAll(photoRepository.findAllForAlbum(album.id))
        }
        Response(Status.OK).with(Body.auto<Any>().toLens() of PhotosActivityReport(photos.size, photos))
    }
}

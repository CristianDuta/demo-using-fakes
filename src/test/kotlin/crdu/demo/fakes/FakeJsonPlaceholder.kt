package crdu.demo.fakes

import org.http4k.core.*
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import org.http4k.template.HandlebarsTemplates
import org.http4k.template.ViewModel
import org.http4k.template.viewModel

data class JsonPlaceholderTemplate(
    val templateName: String,
    val resourceId: String? = null
) : ViewModel {
    override fun template(): String = "crdu/demo/fakes/$templateName"
}

class FakeJsonPlaceholder : HttpHandler {
    private val viewLens = Body.viewModel(
        HandlebarsTemplates {
            it.loader.suffix = ".json"
            it
        }.CachingClasspath(),
        ContentType.APPLICATION_JSON
    ).toLens()

    private val routes = routes(
        "/{name}" bind Method.GET to {
            val name = it.path("name")!!

            Response(Status.OK).with(viewLens of JsonPlaceholderTemplate(name))
        },
        "/{name}/{id}" bind Method.GET to {
            val name = it.path("name")!!
            val id = it.path("id")!!

            Response(Status.OK).with(viewLens of JsonPlaceholderTemplate("${name}-id", id))
        },
        "/{name}/{id}/{resource}" bind Method.GET to {
            val name = it.path("name")!!
            val id = it.path("id")!!
            val resource = it.path("resource")!!

            Response(Status.OK).with(viewLens of JsonPlaceholderTemplate("${name}-id-${resource}", id))
        }
    )

    override fun invoke(request: Request): Response = routes(request)
}

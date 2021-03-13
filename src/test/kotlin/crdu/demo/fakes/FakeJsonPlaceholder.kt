package crdu.demo.fakes

import org.http4k.core.*
import org.http4k.lens.Path
import org.http4k.lens.int
import org.http4k.lens.string
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.template.HandlebarsTemplates
import org.http4k.template.ViewModel
import org.http4k.template.viewModel

data class JsonPlaceholderTemplate(
    val templateName: String,
    val resourceId: Int? = null
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
            val name = Path.string().of("name")(it)

            Response(Status.OK).with(viewLens of JsonPlaceholderTemplate(name))
        },
        "/{name}/{id}" bind Method.GET to {
            val name = Path.string().of("name")(it)
            val id = Path.int().of("id")(it)

            Response(Status.OK).with(viewLens of JsonPlaceholderTemplate("${name}-id", id))
        },
        "/{name}/{id}/{resource}" bind Method.GET to {
            val name = Path.string().of("name")(it)
            val id = Path.int().of("id")(it)
            val resource = Path.string().of("resource")(it)

            Response(Status.OK).with(viewLens of JsonPlaceholderTemplate("${name}-id-${resource}", id))
        }
    )

    override fun invoke(request: Request): Response = routes(request)
}

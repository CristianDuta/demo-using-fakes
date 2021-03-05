package crdu.demo.datasources

import crdu.demo.entities.Entity
import org.http4k.core.Body
import org.http4k.core.ContentType.Companion.APPLICATION_JSON
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.format.Jackson.asA
import org.http4k.format.Jackson.asFormatString
import org.http4k.format.Jackson.auto
import org.http4k.lens.string
import kotlin.reflect.KClass

class JsonPlaceholderDataSource<T : Entity>(
    private val url: String,
    private val httpClient: HttpHandler,
    private val clazz: KClass<T>
) : DataSource<T> {
    override fun <T> fetchDataForCollection(collectionName: String): List<T> {
        val response = doGetRequest(collectionName)

        return Body.auto<List<T>>().toLens()(response)
    }

    override fun fetchDataForCollectionItem(collectionName: String, itemId: Int): T {
        val response = doGetRequest("$collectionName/$itemId")
        val lens = Body.string(APPLICATION_JSON).map({ asA(it, clazz) }, { asFormatString(it) }).toLens()
        return lens(response)
    }

    override fun <T> fetchDataForCollectionInRelationTo(
        collectionName: String,
        inRelationTo: Pair<String, Int>
    ): List<T> {
        val (collection, itemId) = inRelationTo

        val response = doGetRequest("$collection/$itemId/$collectionName")

        return Body.auto<List<T>>().toLens()(response)
    }

    private fun doGetRequest(endpoint: String) = httpClient(
        Request(Method.GET, "$url/$endpoint")
            .header("Accept", APPLICATION_JSON.toHeaderValue())
    )
}

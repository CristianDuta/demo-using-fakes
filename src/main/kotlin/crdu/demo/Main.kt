package crdu.demo

import org.http4k.client.ApacheClient
import org.http4k.cloudnative.env.Environment
import org.http4k.cloudnative.env.EnvironmentKey
import org.http4k.lens.boolean
import org.http4k.server.SunHttp
import org.http4k.server.asServer

object EnvironmentKeys {
    val DEBUG = EnvironmentKey.boolean().required("DEBUG")
    val REPOSITORY_API_URL = EnvironmentKey.required("REPOSITORY_API_URL")
}

fun main() {
    val environment: Environment =
        Environment.fromResource("app.properties") overrides
                Environment.JVM_PROPERTIES overrides
                Environment.ENV
    app(
        environment,
        ApacheClient()
    ).asServer(SunHttp(8080)).start()
}

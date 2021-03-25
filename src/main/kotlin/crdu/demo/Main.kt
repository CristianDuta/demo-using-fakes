package crdu.demo

import org.http4k.client.ApacheClient
import org.http4k.cloudnative.env.Environment
import org.http4k.server.SunHttp
import org.http4k.server.asServer

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

package crdu.demo

import crdu.demo.fakes.FakeJsonPlaceholder
import org.http4k.client.ApacheClient
import org.http4k.cloudnative.env.Environment
import org.http4k.core.with
import org.http4k.server.SunHttp
import org.http4k.server.asServer

fun main() {
    val port = FakeJsonPlaceholder().asServer(SunHttp(0)).start().port()
    val env = Environment.EMPTY
        .with(
            EnvironmentKeys.DEBUG of false,
            EnvironmentKeys.REPOSITORY_API_URL of "http://localhost:$port"
        )

    app(
        env,
        ApacheClient()
    ).asServer(SunHttp(8080)).start()
}

package crdu.demo.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status

fun overallActivityReportHandler(): HttpHandler {
    return {
        Response(Status.OK)
    }
}

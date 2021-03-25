package crdu.demo

import org.http4k.cloudnative.env.EnvironmentKey
import org.http4k.lens.boolean

object EnvironmentKeys {
    val DEBUG = EnvironmentKey.boolean().defaulted("DEBUG", false)
    val REPOSITORY_API_URL = EnvironmentKey.required("REPOSITORY_API_URL")
}
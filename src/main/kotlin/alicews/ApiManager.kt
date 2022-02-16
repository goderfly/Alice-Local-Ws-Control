package alicews
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result

class ApiManager {

    fun getDeviceList(): Triple<Request, Response, Result<String, FuelError>> =
            (GLAGOL_API_BASE_URL + GLAGOL_API_DEVECE_URL_SUFFIX)
                    .httpGet()
                    .header(
                            "Authorization" to OAUTH_TOKEN_HEADER,
                            "Content-Type" to "application/json"
                    )
                    .responseString()

    fun getJwtToken(device_id: String, platform: String): Triple<Request, Response, Result<String, FuelError>> =
            (GLAGOL_API_BASE_URL + GLAGOL_API_JWT_URL_SUFFIX)
                    .httpGet(listOf(
                            "device_id" to device_id,
                            "platform" to platform)
                    )
                    .header(
                            "Authorization" to OAUTH_TOKEN_HEADER,
                            "Content-Type" to "application/json"
                    )
                    .responseString()

    companion object {
        private val OAUTH_TOKEN_HEADER = "Oauth AgAAAAADEWCDAAG8XlTtR80RFEQmhLhJVin7c94"


        private const val GLAGOL_API_BASE_URL = "https://quasar.yandex.net/glagol"
        private const val GLAGOL_API_DEVECE_URL_SUFFIX = "/device_list"
        private const val GLAGOL_API_JWT_URL_SUFFIX = "/token"
    }
}
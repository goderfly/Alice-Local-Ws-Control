package alicews

import alicews.models.genious.search.SearchResponseModel
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson

object ReceiveLyricsInteractor {

    fun getLyricsBySingName(name: String): String {
        val songPath = getSongByQuery(name)
        return getLyricsBySongPath(songPath)
    }

    private fun getLyricsBySongPath(path: String?): String {
        val responseString = "https://genius.com$path".httpGet()
            .responseString().third.component1()
        val entry =
            responseString!!.substringAfter("\"lyrics_ctrl.update_selection_range(ranges[0])\" class=\"\"><p>")
        val result = entry
            .substringAfter("data-lyrics-container=\"true\"")
            .substringAfter("\">")
            .substringBefore("<div class=\"RightSidebar__Container")
            .replace("<br/>", "\n")
            .replace("&#x27;", "'")
            .replace("&quot;", "\"")
            .replace("</div>", " ")

        var isMatch = true
        while (isMatch) {
            runCatching {
                result.substring(result.indexOfFirst { it in "<" }..result.indexOfFirst { it in ">" })
            }.onSuccess {
                result.replace(it, "")
            }.onFailure {
                println(it)
                isMatch = false
            }
        }

        return result
    }

    private fun getSongByQuery(song: String): String? {
        val responseString = "https://api.genius.com/search?q=$song".httpGet()
            .header(
                "Authorization" to "Bearer 5TQxk63ZJoaUE2VqBPeVBTejIta4rzgxIl2m-afyCZ10o4RIVCaXbau0IwVhlb3e"
            )
            .responseString().third.component1()


        val responseObject: SearchResponseModel =
            Gson().fromJson(responseString, SearchResponseModel::class.java)
        val path = responseObject.response?.hits?.get(0)?.result?.path
        return path
    }
}
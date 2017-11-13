package lcleite.github.com.helloandroidkotlin.mapper

import org.json.JSONObject


interface JsonMapper<T> {
    fun toModel(jsonObject: JSONObject): T
}

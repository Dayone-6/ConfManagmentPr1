import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.io.File

class Settings(
    private val settingsPath: String,
    private val args: Array<String>
) {
    private var settings: JsonObject? = null
    private lateinit var arguments: Map<String, String>

    init {
        try {
            arguments = args.toList().chunked(2).associate { it[0] to it[1] }
            val settingsFile = File(settingsPath)
            settings = Json.decodeFromString<JsonObject>(settingsFile.readText())
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun getAllArguments(): Map<String, String> = arguments

    fun getSetting(name: String): String? {
        if(arguments.isNotEmpty()){
            if(arguments.containsKey(name)){
                return arguments[name]
            }
        }
        if(settings == null || !settings!!.containsKey(name)) return null
        return settings!![name]!!.jsonPrimitive.content
    }
}
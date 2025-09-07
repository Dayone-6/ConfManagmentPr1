import java.io.File
import java.net.InetAddress
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

fun main() {
    val username = System.getProperty("user.name")
    val hostname = InetAddress.getLocalHost().hostName

    val settings = File("C:\\Projects\\IdeaProjects\\configurationManagmentPr1\\src\\settings.json")
    val settingsJson = Json.decodeFromString<JsonObject>(settings.readText())
    val vfsPath = settingsJson["vfsPath"]!!.jsonPrimitive.content
    val startScriptPath = settingsJson["commandLine"]!!.jsonPrimitive.content

    val terminal = Terminal(username, hostname, startScriptPath, vfsPath)
    terminal.start()
}

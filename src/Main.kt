import exceptions.VFSNotFoundException
import java.net.InetAddress

fun main(args: Array<String>) {
    val username = System.getProperty("user.name")
    val hostname = InetAddress.getLocalHost().hostName

    val settings = Settings(args)
    val vfsPath = settings.getSetting("-vfsPath") ?: ""
    val startScriptPath = settings.getSetting("-startScript") ?: ""

    try {
        val terminal = Terminal(username, hostname, startScriptPath, vfsPath)
        terminal.start()
    }catch (e: VFSNotFoundException){
        printErrorLine(e.message!!)
    }
}

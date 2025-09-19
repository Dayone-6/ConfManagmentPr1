import java.net.InetAddress

const val SETTINGS_PATH = "C:\\Users\\leoni\\OneDrive\\Desktop\\University\\Conf managment\\configurationManagmentPr1\\src\\settings.json"

fun main(args: Array<String>) {
    val username = System.getProperty("user.name")
    val hostname = InetAddress.getLocalHost().hostName

    val settings = Settings(SETTINGS_PATH, args)
    val vfsPath = settings.getSetting("vfsPath") ?: ""
    val startScriptPath = settings.getSetting("startScriptPath") ?: ""

    println("All passed arguments")
    if(settings.getAllArguments().isEmpty()){
        println("No arguments passed")
    }
    settings.getAllArguments().forEach { println(it.key + " " + it.value) }

    val terminal = Terminal(username, hostname, startScriptPath, vfsPath)
    terminal.start()
}

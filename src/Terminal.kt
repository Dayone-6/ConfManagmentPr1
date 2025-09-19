import java.io.File
import java.util.zip.ZipFile

class Terminal(
    private val userName: String,
    private val hostName: String,
    private val startScriptPath: String,
    private val vfsPath: String,
) {
    private var resolver: Resolver = Resolver(vfsPath)
    private var startScript: List<String>? = null

    init {
        try {
            if(startScriptPath.isNotEmpty()) {
                val startScriptFile = File(startScriptPath)
                val script = startScriptFile.readText()
                if(script.isNotEmpty()) {
                    startScript = startScriptFile.readText().split("\n")
                }
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun start() {
        runStartScript()
        while(true){
            print("$hostName@$userName ${resolver.getCurrentDirectory().replace(vfsPath, "vfs").split("\\").last().paint(34)}:~$ ")
            val commandLine = readln()
            resolver.resolveCommand(commandLine)
        }
    }

    private fun runStartScript() {
        if(startScript != null){
            println("Start of start script")
            var code = 0
            for(command in startScript){
                print("$hostName@$userName ${resolver.getCurrentDirectory().replace(vfsPath, "vfs").split("\\").last().paint(34)}:~$ $command")
                code = resolver.resolveCommand(command)
                if(code != 0){
                    break
                }
            }
            if(code != 0){
                println("Start script failed!")
            }else{
                println("End of start script")
            }
        }
    }
}
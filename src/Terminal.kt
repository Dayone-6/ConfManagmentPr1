import java.io.File

class Terminal(
    private val userName: String,
    private val hostName: String,
    private val startScriptPath: String,
    private val vfsPath: String,
) {
    private var resolver: Resolver = Resolver()
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
            val vfs = File(vfsPath)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun start() {
        runStartScript()
        while(true){
            print("PC ${resolver.getCurrentDirectory()}> ")
            val commandLine = readln()
            resolver.resolveCommand(commandLine)
        }
    }

    private fun runStartScript() {
        if(startScript != null){
            println("Начало стартового скрипта")
            var code = 0
            for(command in startScript){
                print("PC ${resolver.getCurrentDirectory()}> $command")
                code = resolver.resolveCommand(command)
                if(code != 0){
                    break
                }
            }
            if(code != 0){
                println("Стартовый скрипт завершился ошибкой!")
            }else{
                println("Конец стартового скрипта")
            }
        }
    }
}
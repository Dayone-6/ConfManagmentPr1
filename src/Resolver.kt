class Resolver(
    private val vfsPath: String,
    private val userName: String
) {
    private val executer = Executer(vfsPath, userName)

    fun getCurrentDirectory(): String = executer.getCurrentDirectory()

    fun resolveCommand(commandLine: String): Int {
        if(commandLine.isBlank()){
            return 1
        }
        val parts = commandLine.trim().split(" ")
        val command = parts[0]
        val args = parts.drop(1)
        try {
            when(command){
                "cd" -> { return executer.cd(args) }
                "ls" -> return executer.ls()
                "help" -> return executer.help()
                "whoami" -> return executer.whoami()
                "tail" -> {
                    return if(args.size == 1) {
                        executer.tail(10, args[0])
                    }else{
                        executer.tail(args[0].toInt(), args[1])
                    }
                }
                "pwd" -> return executer.pwd()
                "exit" -> return executer.exit()
            }
        }catch (e: Exception){
            printErrorLine("$command: ${e.message}")
            return 1
        }
        printErrorLine("Unresolved command")
        return 0
    }

}
class Resolver(
    private val vfsPath: String
) {
    private val executer = Executer(vfsPath)

    fun getCurrentDirectory(): String = executer.getCurrentDirectory()

    fun resolveCommand(commandLine: String): Int {
        if(commandLine.isBlank()){
            return 1
        }
        val parts = commandLine.trim().split(" ")
        val command = parts[0]
        val args = parts.drop(1)
        when(command){
            "cd" -> return executer.cd(args)
            "ls" -> return executer.ls()
            "help" -> return executer.help()
            "exit" -> return executer.exit()
        }
        println("Неизвестная команда")
        return 0
    }

}
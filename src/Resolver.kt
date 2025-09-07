class Resolver {
    private val executer = Executer()

    fun resolveCommand(commandLine: String): Int {
        if(commandLine.isBlank()){
            return 1
        }
        val parts = commandLine.trim().split(" ")
        val command = parts[0]
        val args = parts.drop(1)
        when(command){
            "cd" -> return executer.cd(args)
            "ls" -> return executer.ls(args)
            "exit" -> return executer.exit()
        }
        println("Неизвестная команда")
        return 0
    }
}
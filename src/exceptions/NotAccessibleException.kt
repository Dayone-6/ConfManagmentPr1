package exceptions

class NotAccessibleException(path: String) : Exception("$path is not accessible")
package day07

import utils.readInput

typealias Path = List<String>

data class File(val name: String, val size: Long)

data class Directory(
    val name: String,
    private val files: MutableList<File> = mutableListOf(),
    private val subdirectories: MutableList<Directory> = mutableListOf(),
) {
    fun addDirectory(directory: Directory) {
        subdirectories.add(directory)
    }

    fun addFile(file: File) {
        files.add(file)
    }

    val totalSize: Long
        get() = files.sumOf(File::size) + subdirectories.sumOf(Directory::totalSize)

    val totalSizes: List<Long>
        get() = subdirectories.flatMap(Directory::totalSizes) + totalSize
}

fun part1(filename: String): Long =
    readInput(filename).readDirectories().totalSizes.filter { it <= 100000 }.sum()

fun part2(filename: String): Long {
    val directories = readInput(filename).readDirectories()
    val unused = 70000000 - directories.totalSize
    val minimalFreeUp = 30000000 - unused
    return directories.totalSizes.filter { it >= minimalFreeUp }.min()
}

private val root = listOf("/")

class DirectoryWalker(
    private val directories: MutableMap<Path, Directory> = mutableMapOf(root to Directory("/")),
    private var path: Path = emptyList()
) {
    fun execute(command: String) =
        when {
            command == "$ cd .." -> moveOut()
            command.startsWith("$ cd ") -> moveIn(command.removePrefix("$ cd "))
            command == "$ ls" -> {}
            command.startsWith("dir ") -> addDirectory(command.removePrefix("dir "))
            else -> {
                val (size, name) = command.split(' ')
                addFile(name, size.toLong())
            }
        }

    private fun addFile(name: String, size: Long) {
        currentDirectory().addFile(File(name, size))
    }

    private fun addDirectory(name: String) {
        val directory = Directory(name)
        directories[path + name] = directory
        currentDirectory().addDirectory(directory)
    }

    private fun currentDirectory() = checkNotNull(directories[path]) { "Missing directory ${path}" }

    private fun moveIn(subDirectory: String) {
        path = path + subDirectory
    }

    private fun moveOut() {
        path = path.subList(0, path.size - 1)
    }

    fun root(): Directory = checkNotNull(directories[root]) { "No root directory found" }
}

fun List<String>.readDirectories(): Directory =
    DirectoryWalker().apply { forEach(::execute) }.root()

const val filename = "Day07"

fun main() {
    println(part1(filename))
    println(part2(filename))
}

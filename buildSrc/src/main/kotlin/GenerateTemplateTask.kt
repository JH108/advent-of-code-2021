import org.gradle.api.DefaultTask
import org.gradle.api.file.Directory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.io.File
import java.nio.file.Files
import java.nio.file.Files.createDirectory
import java.nio.file.Files.deleteIfExists
import java.nio.file.Path
import kotlin.io.path.Path

abstract class GenerateTemplateTask : DefaultTask() {
    @get:InputFile
    abstract val kotlinTemplate: RegularFileProperty

    @get:Input
    @Option(description = "The day to generate")
    var day: String = "nope"

    private fun cleanup(packagePath: String, fileName: String) {
        println("Cleaning Package")
        println("Deleting Kotlin File")
        deleteIfExists(Path("$packagePath/$fileName.kt"))
        println("Deleting Input File")
        deleteIfExists(Path("$packagePath/$fileName.txt"))
        println("Deleting Test Input File")
        deleteIfExists(Path("$packagePath/${fileName}_test.txt"))
        println("Deleting Package Directory")
        deleteIfExists(Path(packagePath))

    }

    @TaskAction
    fun taskAction() {
        println("Generating")
        val templateText = kotlinTemplate.get().asFile.readText()
        val packageName = "day$day"
        val fileName = "Day$day"
        val kotlinText = templateText
            .replace("{packageName}", packageName)
            .replace("{fileName}", fileName)
        // create dir in src for day
        val packagePath = "${project.projectDir.absolutePath}/src/$packageName"

        cleanup(packagePath, fileName)

        println("Creating Package Directory")
        val packageDir = createDirectory(Path(packagePath))
        println("New Package: $packageDir")
        // put kotlin template in new dir
        println("Creating Kotlin File")
        File("$packagePath/$fileName.kt").writeText(kotlinText)

        println("Creating Text File")
        File("$packagePath/$fileName.txt").writeText("")
        println("Creating Test Text File")
        File("$packagePath/${fileName}_test.txt").writeText("")

        println("Finished")
    }
}
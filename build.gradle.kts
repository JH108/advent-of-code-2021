plugins {
    kotlin("jvm") version "1.6.0"

    application
}

repositories {
    mavenCentral()
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "7.3"
    }
}

application {
    mainClass.set("day05/Day05Kt")
}

tasks.register<GenerateTemplateTask>("generate") {
    println("Setting Templates")
    val templateDir = project.projectDir.resolve("templates")
    val kotlinFile = templateDir.resolve("Day_template.kt.txt")

    println("Template File: $kotlinFile")

    kotlinTemplate.set(kotlinFile)
}

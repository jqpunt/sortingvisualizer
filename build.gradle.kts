plugins {
    kotlin("jvm") version "1.9.22"
    application
    jacoco
}

group = "nl.justin.sortingvisualizer"
version = "1.0.0"

repositories {
    mavenCentral()
}

val osName = System.getProperty("os.name").lowercase()
val arch = System.getProperty("os.arch").lowercase()

val javafxPlatform = when {
    System.getProperty("os.name").lowercase().contains("mac") &&
            System.getProperty("os.arch").contains("aarch64") -> "mac-aarch64"
    System.getProperty("os.name").lowercase().contains("mac") -> "mac"
    System.getProperty("os.name").lowercase().contains("win") -> "win"
    else -> "linux"
}



dependencies {
    // Kotlin test (integreert met JUnit 5)
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.9.22")

    // JUnit 5 BOM voor consistente versies
    testImplementation(platform("org.junit:junit-bom:5.10.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // JavaFX — ALLE modules die je code gebruikt
    implementation("org.openjfx:javafx-base:21:$javafxPlatform")
    implementation("org.openjfx:javafx-graphics:21:$javafxPlatform")
    implementation("org.openjfx:javafx-controls:21:$javafxPlatform")
    implementation("org.openjfx:javafx-fxml:21:$javafxPlatform")
}


application {
    mainClass.set("nl.justin.sortingvisualizer.app.MainAppKt")
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

jacoco {
    toolVersion = "0.8.11"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}


tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            element = "CLASS"
            includes = listOf(
                "nl.justin.sortingvisualizer.model.BubbleSort",
                "nl.justin.sortingvisualizer.model.InsertionSort"
            )
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.90".toBigDecimal()
            }
        }
    }
}

// Check coverage
tasks.check {
    dependsOn(tasks.jacocoTestCoverageVerification)
}

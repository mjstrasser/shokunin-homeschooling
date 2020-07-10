plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.3.72"

    // Apply the application plugin to add support for building a CLI application.
    application
}

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
}

dependencies {
    val kotlinVersion = "1.3.72"
    val junit5Version = "5.6.0"
    val spekVersion = "2.0.9"
    val assertkVersion = "0.22"

    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation(group = "org.jetbrains.kotlin", name = "kotlin-stdlib-jdk8", version = kotlinVersion)

    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-api", version = junit5Version)
    testRuntimeOnly(group = "org.junit.jupiter", name = "junit-jupiter-engine", version = junit5Version)

    testImplementation(group = "com.willowtreeapps.assertk", name = "assertk-jvm", version = assertkVersion)

    // Override the version in Spek.
    testImplementation(kotlin(module = "reflect", version = kotlinVersion))
    testImplementation(group = "org.spekframework.spek2", name = "spek-dsl-jvm", version = spekVersion)
    testRuntimeOnly(group = "org.spekframework.spek2", name = "spek-runner-junit5", version = spekVersion)
}

tasks.test {
    useJUnitPlatform()
    dependsOn("cleanTest")
}

application {
    // Define the main class for the application.
    mainClassName = "mjs.homeschooling.AppKt"
}

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.72"

    application
}

repositories {
    jcenter()
    maven(url = "https://kotlin.bintray.com/kotlinx")
}

dependencies {
    val kotlinVersion = "1.3.72"
    val cliktVersion = "2.8.0"
    val junit5Version = "5.6.0"
    val spekVersion = "2.0.9"
    val assertkVersion = "0.22"

    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation(group = "org.jetbrains.kotlin", name = "kotlin-stdlib-jdk8", version = kotlinVersion)

    implementation(group = "com.github.ajalt", name = "clikt", version = cliktVersion)

    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-api", version = junit5Version)
    testRuntimeOnly(group = "org.junit.jupiter", name = "junit-jupiter-engine", version = junit5Version)

    testImplementation(group = "com.willowtreeapps.assertk", name = "assertk-jvm", version = assertkVersion)

    testImplementation(kotlin(module = "reflect", version = kotlinVersion))
    testImplementation(group = "org.spekframework.spek2", name = "spek-dsl-jvm", version = spekVersion)
    testRuntimeOnly(group = "org.spekframework.spek2", name = "spek-runner-junit5", version = spekVersion)
}

tasks.test {
    useJUnitPlatform()
    dependsOn("cleanTest")
}

application {
    mainClassName = "mjs.homeschooling.HomeSchoolingKt"
}

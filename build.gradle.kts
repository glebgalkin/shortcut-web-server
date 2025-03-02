plugins {
    id("application")
    id("java")
    id("com.github.spotbugs") version "6.1.6"
}

group = "com.gleb"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.mockito:mockito-core:2.1.0")

    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")

    // SLF4J API
    implementation("org.slf4j:slf4j-api:2.0.12")

    // Log4j (Logging Implementation for SLF4J)
    implementation("org.apache.logging.log4j:log4j-api:2.24.3")
    implementation("org.apache.logging.log4j:log4j-core:2.24.3")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.24.3")
}

application {
    mainClass.set("com.gleb.web.WebServerApplication")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "com.gleb.web.WebServerApplication"
    }
}

tasks.test {
    useJUnitPlatform() // Ensures JUnit 5 is used
}
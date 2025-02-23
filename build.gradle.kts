plugins {
    id("application")
    id("java")
}

group = "com.gleb"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
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

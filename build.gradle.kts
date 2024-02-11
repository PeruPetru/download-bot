plugins {
    id("java")
}

group = "com.petrustoica"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.discord4j:discord4j-core:3.2.6")
}

tasks.test {
    useJUnitPlatform()
}
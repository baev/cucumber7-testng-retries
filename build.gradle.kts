plugins {
    java
}

tasks.withType(Wrapper::class) {
    gradleVersion = "7.5.1"
}

group = "io.qameta.allure.examples"
version = 1.3

val allureVersion = "2.24.0"
val cucumberVersion = "7.13.0"

tasks.withType(JavaCompile::class) {
    sourceCompatibility = "${JavaVersion.VERSION_11}"
    targetCompatibility = "${JavaVersion.VERSION_11}"
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }
    compileTestJava {
        options.encoding = "UTF-8"
    }
}

tasks.withType(Test::class) {
    ignoreFailures = true
    useTestNG {

    }
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    testImplementation("io.cucumber:cucumber-testng:$cucumberVersion")
    testImplementation("io.cucumber:cucumber-java:$cucumberVersion")

    testImplementation("io.qameta.allure:allure-cucumber7-jvm:$allureVersion")
    testImplementation("io.qameta.allure:allure-testng:$allureVersion")

    testImplementation("org.slf4j:slf4j-simple:1.7.30")
}

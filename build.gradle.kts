plugins {
    java
    application
    id("org.javamodularity.moduleplugin") version "1.8.15"
    id("org.openjfx.javafxplugin") version "0.0.13"
    id("org.beryx.jlink") version "2.25.0"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val junitVersion = "5.12.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

application {
    mainModule.set("com.example.chaotixweatherapp")
    mainClass.set("com.example.chaotixweatherapp.HelloApplication")
}

javafx {
    version = "21.0.6"
    modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")

    /*
    //Für die LLM API wird folgendes benötigt:
    implementation("com.theokanning.openai-gpt3-java:client:0.18.2")


    //Test2:
    implementation("io.github.sashirestela:simple-openai:[simple-openai_latest_version]")
    /* OkHttp dependency is optional if you decide to use it with simple-openai */
    implementation("com.squareup.okhttp3:okhttp:[okhttp_latest_version]")
    */
    //Test3:
    implementation("com.google.code.gson:gson:2.10.1")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jlink {
    imageZip.set(layout.buildDirectory.file("/distributions/app-${javafx.platform.classifier}.zip"))
    options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))
    launcher {
        name = "app"
    }
}

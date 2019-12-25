plugins {
    application
    id("email.haemmerle.baseplugin").version("1.0.3")
}

group = "email.haemmerle.restclient"
description = "RESTful HTTP Client Library"

application {
    mainClassName = "email.haemmerle.talkplanning.AppKt"
}

`email-haemmerle-base`{
    username = "mhmmerle"
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation(platform("org.apache.logging.log4j:log4j-bom:2.12.1"))
    testImplementation(platform("org.junit:junit-bom:5.5.2"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.apache.logging.log4j:log4j-api")
    runtimeOnly("org.apache.logging.log4j:log4j-core")
    runtimeOnly("org.apache.logging.log4j:log4j-slf4j-impl")
    implementation("no.tornado:tornadofx:1.7.19")
    runtimeOnly( "com.h2database:h2:1.4.200")
    runtimeOnly("org.jetbrains.exposed", "exposed-core", "0.18.1")
    implementation("org.jetbrains.exposed", "exposed-dao", "0.18.1")
    implementation("org.jetbrains.exposed", "exposed-jdbc", "0.18.1")

    //testImplementation("org.junit.jupiter:junit-jupiter-api")
    //testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.assertj:assertj-core:3.11.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

java {
    withJavadocJar()
    withSourcesJar()
}
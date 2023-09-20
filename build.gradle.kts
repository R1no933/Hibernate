plugins {
    id("java")
}

group = "hibernate"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation ("org.hibernate:hibernate-core:5.5.6.Final")
    runtimeOnly ("org.postgresql:postgresql:42.2.23.jre7")
    implementation ("com.vladmihalcea:hibernate-types-52:2.12.1")
    implementation("org.slf4j:slf4j-log4j12:2.0.7")


    compileOnly ("org.projectlombok:lombok:1.18.20")
    annotationProcessor ("org.projectlombok:lombok:1.18.20")

    testCompileOnly ("org.projectlombok:lombok:1.18.20")
    testAnnotationProcessor ("org.projectlombok:lombok:1.18.20")
    testImplementation("org.assertj:assertj-core:3.21.0")
    testImplementation ("org.junit.jupiter:junit-jupiter-api:5.7.2")
    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.7.2")
    testImplementation("org.testcontainers:postgresql:1.19.0")


}

tasks.test {
    useJUnitPlatform()
}
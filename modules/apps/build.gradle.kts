import org.springframework.boot.gradle.tasks.bundling.BootJar

subprojects {
  apply(plugin = "org.springframework.boot")

  dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-json")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
  }

  tasks.withType<BootJar> {
    launchScript()
  }

  apply(from = "${rootProject.projectDir}/gradle/spotbugs.gradle")
}

apply(from = "${rootProject.projectDir}/gradle/jacoco.gradle")

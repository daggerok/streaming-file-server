subprojects {
  apply<JavaPlugin>()
  apply<org.springframework.boot.gradle.plugin.SpringBootPlugin>()

  dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-json")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
  }

  tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    launchScript()
  }
}

apply<JavaPlugin>()
apply(from = "${rootProject.projectDir}/gradle/jacoco.gradle")

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

subprojects {
  // Do not generate `*-plain.jar` files. See: https://stackoverflow.com/a/68786767/1490636
  // tasks.jar {
  //   enabled = false
  //   archiveClassifier.set("")
  // }
  tasks {
    // '*-plain.jar' -> '*-plain.zip'
    named<Jar>("jar") {
        archiveExtension.set("zip")
    }
  }
}

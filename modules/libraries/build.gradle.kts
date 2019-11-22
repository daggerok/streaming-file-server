plugins {
  `java-library`
}

subprojects {
  apply<JavaLibraryPlugin>()

  dependencies {
    api("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
  }

  tasks {
    jar.get().enabled = true
    withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
      enabled = false
    }
  }
}

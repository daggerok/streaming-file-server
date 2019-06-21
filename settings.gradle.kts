pluginManagement {
  repositories {
    mavenCentral()
    gradlePluginPortal()
    maven(url = "https://plugins.gradle.org/m2/")
    maven(url = "https://repo.spring.io/milestone/")
    //maven(url = "https://repo.spring.io/snapshots/")
    jcenter()
  }
  resolutionStrategy {
    eachPlugin {
      if (requested.id.id == "org.springframework.boot") {
        useModule("org.springframework.boot:spring-boot-gradle-plugin:${requested.version}")
      }
    }
  }
}

rootProject.name = Globals.Project.artifactId

include(
    ":modules:libraries:props",
    ":modules:libraries:web-security",

    ":modules:apps:file-items-service",
    ":modules:apps:file-server",

    ":modules:docker:postgres",
    ":modules:docker:all"
)

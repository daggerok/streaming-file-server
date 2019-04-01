pluginManagement {
  repositories {
    mavenCentral()
    gradlePluginPortal()
    maven {
      url = uri("https://plugins.gradle.org/m2/")
    }
  }
}

rootProject.name = "streaming-file-server"

include(
    ":modules:libraries:props",
    ":modules:libraries:web-security",
    ":modules:apps:file-items-service",
    ":modules:apps:file-server",

    ":modules:docker:postgres",
    ":modules:docker:all"
)

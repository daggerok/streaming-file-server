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

rootProject.name = "streaming-file-server"
// https://docs.gradle.org/current/userguide/upgrading_version_5.html#classes_from_buildsrc_are_no_longer_visible_to_settings_scripts
// object GlobalSettings {
//   const val artifactId = "streaming-file-server"
// }
// rootProject.name = GlobalSettings.artifactId

include(
    ":modules:libraries:props",
    ":modules:libraries:web-security",

    ":modules:apps:file-items-service",
    ":modules:apps:file-server",

    ":modules:docker:postgres",
    ":modules:docker:all"
)

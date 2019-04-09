buildscript {
  val gradleVersionsPluginVersion: String by project

  repositories {
    gradlePluginPortal()
  }

  dependencies {
    classpath("com.github.ben-manes:gradle-versions-plugin:$gradleVersionsPluginVersion")
  }
}

apply(plugin = "com.github.ben-manes.versions")

// gradle dependencyUpdates -Drevision=release --parallel
tasks.named<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask>("dependencyUpdates") {
  resolutionStrategy {
    componentSelection {
      all {
        val rejected = listOf("alpha", "beta", "rc", "cr", "m", "preview", "b", "ea", "M1", "BUILD-SNAPSHOT", "SNAPSHOT")
            .map { qualifier -> Regex("(?i).*[.-]$qualifier[.\\d-+]*") }
            .any { it.matches(candidate.version) }
        println("rejected: $rejected for: ${candidate.version}")
        if (rejected) reject("Release candidate")
      }
    }
  }
  //// optionals:
  // checkForGradleUpdate = true
  // outputFormatter = "plain" // "json" // "xml"
  // outputDir = "build/dependencyUpdates"
  // reportfileName = "report"
}

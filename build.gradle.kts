plugins {
  idea
  eclipse
  `java-library`
  id("org.ajoberstar.grgit")
  id("org.ajoberstar.reckon")
  id("io.franzbecker.gradle-lombok")
  id("com.github.ben-manes.versions")
  id("io.spring.dependency-management")
  id("org.asciidoctor.jvm.pdf") apply false
  id("org.springframework.boot") apply false
  id("org.asciidoctor.jvm.gems") apply false
  id("org.asciidoctor.jvm.convert") apply false
  id("com.avast.gradle.docker-compose") apply false
  id("com.ewerk.gradle.plugins.querydsl") apply false
}

val groupId: String by project
val lombokVersion: String by project
val projectVersion: String by project
val wrapperVersion: String by project
val postgresVersion: String by project

extra["postgresql.version"] = postgresVersion

allprojects {
  group = groupId
  version = projectVersion // because of reckon!
  defaultTasks("clean", "build")

  apply<io.franzbecker.gradle.lombok.LombokPlugin>()

  lombok {
    version = lombokVersion
  }
}

apply(from = "$rootDir/gradle/ide.gradle")
apply(from = "$rootDir/gradle/clean.gradle")
apply(from = "$rootDir/gradle/subprojects.gradle")
apply(from = "$rootDir/gradle/documentation.gradle")

// gradle dependencyUpdates -Drevision=release --parallel
tasks {
  named<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask>("dependencyUpdates") {
    resolutionStrategy {
      componentSelection {
        all {
          // val rejected = listOf("alpha", "beta", "rc", "cr", "m", "preview", "b", "ea", "M1", "BUILD-SNAPSHOT", "SNAPSHOT")
          val rejectedVersions = listOf("alpha", "M") // ch.qos.logback:logback-classic:1.3.0-alpha*, io.vavr:vavr:1.0.0-alpha-*
            // com.tngtech.jgiven:jgiven-*:1.0.0-RC1 com.tngtech.jgiven:jgiven-*:1.0.0-RC2
            .map { qualifier -> Regex("(?i).*[.-]$qualifier[.\\d-+]*") }
            .any { it.matches(candidate.version) }
          val rejectedModules = listOf("com.avast.gradle.docker-compose.gradle.plugin")
            .plus("org.ajoberstar.reckon.gradle.plugin")
            .plus("org.ajoberstar.grgit.gradle.plugin")
            .any { candidate.module.contains(it) }
          if (rejectedVersions or rejectedModules) reject("Release candidate")
        }
      }
    }
    outputFormatter = "plain" // "json"
  }
  withType<Test> {
    useJUnitPlatform()
    testLogging {
      showExceptions = true
      showStandardStreams = true
      events(
          org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
          org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED,
          org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
      )
    }
  }
  withType<Wrapper> {
    gradleVersion = wrapperVersion
    distributionType = Wrapper.DistributionType.BIN
  }
  register("version") {
    println(project.version.toString())
  }
  register("status") {
    doLast {
      val status = grgit.status() ?: return@doLast println("no unstaged changes")

      println("workspace is clean: ${status.isClean}")
      if (status.isClean or status.unstaged.allChanges.isEmpty()) return@doLast println("no unstaged changes")

      val result = status.unstaged.allChanges.joinToString(separator = "") { "\n- $it" }
      println("""all unstaged changes: $result""")
    }
  }
}

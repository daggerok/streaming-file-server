buildscript {
  repositories {
    gradlePluginPortal()
  }
  dependencies {
    classpath("gradle.plugin.com.github.spotbugs:spotbugs-gradle-plugin:${Globals.spotbugsVersion}")
  }
}

plugins {
  idea
  maven
  eclipse
  `java-library`
  id("com.github.ben-manes.versions") version Globals.versionsVersion
  id("io.franzbecker.gradle-lombok") version Globals.lombokPluginVersion
  id("org.springframework.boot") version Globals.springBootVersion apply false
  id("org.asciidoctor.convert") version Globals.asciidoctorjConvertVersion apply false
  id("com.avast.gradle.docker-compose") version Globals.dockerComposeVersion apply false
  id("com.ewerk.gradle.plugins.querydsl") version Globals.querydslVersion apply false
  id("io.spring.dependency-management") version Globals.dependencyManagementVersion
}

extra["lombok.version"] = Globals.lombokVersion
extra["postgresql.version"] = Globals.postgresVersion

allprojects {
  version = Globals.version
  group = Globals.groupId
  defaultTasks("clean", "build")
}

tasks.withType<Wrapper> {
  gradleVersion = Globals.wrapperVersion
  distributionType = Wrapper.DistributionType.BIN
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
          val rejected = listOf("alpha") // ch.qos.logback:logback-classic:1.3.0-alpha*, io.vavr:vavr:1.0.0-alpha-*
                  // com.tngtech.jgiven:jgiven-*:1.0.0-RC1 com.tngtech.jgiven:jgiven-*:1.0.0-RC2
              .map { qualifier -> Regex("(?i).*[.-]$qualifier[.\\d-+]*") }
              .any { it.matches(candidate.version) }
          if (rejected) reject("Release candidate")
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
}

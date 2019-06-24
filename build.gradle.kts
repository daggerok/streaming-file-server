import org.gradle.api.tasks.testing.logging.TestLogEvent.*

buildscript {
  repositories {
    gradlePluginPortal()
  }

  dependencies {
    classpath("gradle.plugin.com.github.spotbugs:spotbugs-gradle-plugin:${Globals.spotbugsVersion}")
    classpath("org.asciidoctor:asciidoctorj-pdf:${Globals.asciidoctorjPdfVersion}")
    classpath("org.jruby:jruby-complete:${Globals.jrubyCompleteVersion}")
  }
}

plugins {
  idea
  maven
  eclipse
  `java-library`
  id("io.freefair.lombok") version Globals.lombokPluginVersion
  id("org.springframework.boot") version Globals.springBootVersion apply false
  id("com.github.ben-manes.versions") version Globals.versionsVersion
  id("org.ajoberstar.git-publish") version Globals.gitPublishVersion apply false
  id("org.asciidoctor.convert") version Globals.asciidoctorjConvertVersion apply false
  id("com.avast.gradle.docker-compose") version Globals.dockerComposeVersion apply false
  id("com.ewerk.gradle.plugins.querydsl") version Globals.querydslVersion apply false
  id("io.spring.dependency-management") version Globals.dependencyManagementVersion
  id("cn.bestwu.propdeps-eclipse") version Globals.propdepsVersion
  id("cn.bestwu.propdeps-maven") version Globals.propdepsVersion
  id("cn.bestwu.propdeps-idea") version Globals.propdepsVersion
  id("cn.bestwu.propdeps") version Globals.propdepsVersion
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

apply(from = "${project.rootDir}/gradle/ide.gradle")
apply(from = "${project.rootDir}/gradle/clean.gradle")
apply(from = "${project.rootDir}/gradle/subprojects.gradle")
apply(from = "${project.rootDir}/gradle/documentation.gradle")

// gradle dependencyUpdates -Drevision=release --parallel
tasks {
  named<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask>("dependencyUpdates") {
    resolutionStrategy {
      componentSelection {
        all {
          val rejected = listOf("alpha", "beta", "rc", "cr", "m", "preview", "b", "ea", "M1", "BUILD-SNAPSHOT", "SNAPSHOT")
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
      events(PASSED, SKIPPED, FAILED)
    }
  }
}

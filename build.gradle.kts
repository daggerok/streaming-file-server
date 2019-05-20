buildscript {
  repositories {
    gradlePluginPortal()
  }

  dependencies {
    classpath("gradle.plugin.com.github.spotbugs:spotbugs-gradle-plugin:${Globals.Gradle.Plugin.spotbugsVersion}")
    classpath("org.asciidoctor:asciidoctorj-pdf:${Globals.Gradle.Plugin.asciidoctorjPdfVersion}")
    classpath("org.jruby:jruby-complete:${Globals.Gradle.Plugin.jrubyCompleteVersion}")
  }
}

plugins {
  idea
  maven
  eclipse
  id("io.freefair.lombok") version Globals.Gradle.Plugin.lombokVersion
  id("org.springframework.boot") version Globals.springBootVersion apply false
  id("com.github.ben-manes.versions") version Globals.Gradle.Plugin.versionsVersion
  id("org.ajoberstar.git-publish") version Globals.Gradle.Plugin.gitPublishVersion apply false
  id("org.asciidoctor.convert") version Globals.Gradle.Plugin.asciidoctorjConvertVersion apply false
  id("com.avast.gradle.docker-compose") version Globals.Gradle.Plugin.dockerComposeVersion apply false
  id("com.ewerk.gradle.plugins.querydsl") version Globals.Gradle.Plugin.querydslVersion apply false
  id("io.spring.dependency-management") version Globals.Gradle.Plugin.dependencyManagementVersion
  id("cn.bestwu.propdeps-eclipse") version Globals.Gradle.Plugin.propdepsVersion
  id("cn.bestwu.propdeps-maven") version Globals.Gradle.Plugin.propdepsVersion
  id("cn.bestwu.propdeps-idea") version Globals.Gradle.Plugin.propdepsVersion
  id("cn.bestwu.propdeps") version Globals.Gradle.Plugin.propdepsVersion
}

extra["javaVersion"] = Globals.javaVersion
extra["vavrVersion"] = Globals.vavrVersion
extra["lombokVersion"] = Globals.lombokVersion
extra["lombok.version"] = Globals.lombokVersion
extra["jqueryVersion"] = Globals.jqueryVersion
extra["popperVersion"] = Globals.popperVersion
extra["jgivenVersion"] = Globals.jgivenVersion
extra["logbackVersion"] = Globals.logbackVersion
extra["selenideVersion"] = Globals.selenideVersion
extra["bootstrapVersion"] = Globals.bootstrapVersion
extra["commonsIoVersion"] = Globals.commonsIoVersion
extra["springBootVersion"] = Globals.springBootVersion
extra["toolVersion"] = Globals.Gradle.Plugin.toolVersion
extra["fontAwesomeVersion"] = Globals.fontAwesomeVersion
extra["powermockitoVersion"] = Globals.powermockitoVersion
extra["hibernateJava8Version"] = Globals.hibernateJava8Version
extra["spotbugsVersion"] = Globals.Gradle.Plugin.spotbugsVersion
extra["bootstrapFileInputVersion"] = Globals.bootstrapFileInputVersion

allprojects {
  version = Globals.Project.version
  group = Globals.Project.groupId
  defaultTasks("clean", "build")
}

tasks.withType<Wrapper> {
  gradleVersion = Globals.Gradle.wrapperVersion
  distributionType = Wrapper.DistributionType.BIN
}

apply(from = "${project.rootDir}/gradle/ide.gradle")
apply(from = "${project.rootDir}/gradle/clean.gradle")
apply(from = "${project.rootDir}/gradle/subprojects.gradle")
apply(from = "${project.rootDir}/gradle/documentation.gradle")

// gradle dependencyUpdates -Drevision=release --parallel
tasks.named<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask>("dependencyUpdates") {
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

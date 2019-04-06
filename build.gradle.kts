import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {
  val asciidoctorjPdfVersion: String by project
  val jrubyCompleteVersion: String by project
  dependencies {
    classpath("org.asciidoctor:asciidoctorj-pdf:$asciidoctorjPdfVersion")
    classpath("org.jruby:jruby-complete:$jrubyCompleteVersion")
  }

  val groovyVersion: String by project
  configurations.all {
    resolutionStrategy {
      force("org.codehaus.groovy:groovy-all:$groovyVersion")
    }
  }
}

plugins {
  idea
  maven
  eclipse
  id("com.github.spotbugs") version "1.7.1"
  id("com.github.ben-manes.versions") version "0.21.0"

  id("org.ajoberstar.git-publish") version "2.1.1" apply false
  id("org.asciidoctor.convert") version "1.5.9.1" apply false // on windows my fail, use instead: 1.5.8.1
  id("com.avast.gradle.docker-compose") version "0.7.1" apply false // any other higher version will fail on adoc task

  id("io.franzbecker.gradle-lombok") version "2.1" apply false
  id("org.springframework.boot") version "2.1.4.RELEASE" apply false
  id("com.ewerk.gradle.plugins.querydsl") version "1.0.10" apply false
  id("io.spring.dependency-management") version "1.0.7.RELEASE" apply false
  id("cn.bestwu.propdeps-eclipse") version "0.0.10"
  id("cn.bestwu.propdeps-maven") version "0.0.10"
  id("cn.bestwu.propdeps-idea") version "0.0.10"
  id("cn.bestwu.propdeps") version "0.0.10"
}

val applicationGroup: String by project
val applicationVersion: String by project
allprojects {
  group = applicationGroup
  version = applicationVersion
  defaultTasks("clean", "build")
}

val gradleWrapperVersion: String by project
tasks.withType<Wrapper> {
  gradleVersion = gradleWrapperVersion
  distributionType = Wrapper.DistributionType.BIN
}

apply(from = "${project.rootDir}/gradle/ide.gradle")
apply(from = "${project.rootDir}/gradle/clean.gradle")
apply(from = "${project.rootDir}/gradle/subprojects.gradle")
apply(from = "${project.rootDir}/gradle/documentation.gradle")

// gradle dependencyUpdates -Drevision=release --parallel
tasks.named<DependencyUpdatesTask>("dependencyUpdates") {
  resolutionStrategy {
    componentSelection {
      all {
        val rejected = listOf("alpha", "beta", "rc", "cr", "m", "preview", "b", "ea")
            .map { qualifier -> Regex("(?i).*[.-]$qualifier[.\\d-+]*") }
            .any { it.matches(candidate.version) }
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

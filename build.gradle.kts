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
  id("com.github.spotbugs") version "1.7.1"
  id("net.saliman.properties") version "1.4.6"
  id("com.github.ben-manes.versions") version "0.21.0"

  id("cn.bestwu.propdeps") version "0.0.10" apply false
  id("cn.bestwu.propdeps-idea") version "0.0.10" apply false
  id("cn.bestwu.propdeps-maven") version "0.0.10" apply false
  id("io.franzbecker.gradle-lombok") version "2.1" apply false
  id("cn.bestwu.propdeps-eclipse") version "0.0.10" apply false
  id("org.springframework.boot") version "2.1.3.RELEASE" apply false
  id("com.ewerk.gradle.plugins.querydsl") version "1.0.10" apply false
  id("io.spring.dependency-management") version "1.0.7.RELEASE" apply false

  id("org.ajoberstar.git-publish") version "2.1.1" /* 2.0.0 OK *//* "2.0.0-rc.2" *//* +0.3.0 +0.2.2 | -0.4.1 */ apply false
  id("org.asciidoctor.convert") version "1.5.9.1" apply false // 2.0.0: failed, on windows my fail use instead: 1.5.8.1
  id("com.avast.gradle.docker-compose") version "0.7.1" apply false // fail with 0.8.9 + asciidoctor ruby
  // gradle -Dorg.gradle.jvmargs="-Xms2g -Xmx2g" -S dependencyUpdates -Drevision=release --parallel
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

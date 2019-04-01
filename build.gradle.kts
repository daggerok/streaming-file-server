val applicationName: String by project
val applicationGroup: String by project
val applicationVersion: String by project

val environmentFileDir: String by project
val propertiesPluginEnvironmentNameProperty: String by project

val asciidoctorjPdfVersion: String by project
val jrubyCompleteVersion: String by project
val groovyVersion: String by project

val springBootVersion: String by project
val commonsIoVersion: String by project
val lombokVersion: String by project
val logbackVersion: String by project
val slf4jVersion: String by project
val vavrVersion: String by project
val toolVersion: String by project
val spotbugsVersion: String by project
val jgivenVersion: String by project
val selenideVersion: String by project
val powermockitoVersion: String by project
val hibernateJava8Version: String by project
val popperVersion: String by project
val jqueryVersion: String by project
val fontAwesomeVersion: String by project
val bootstrapVersion: String by project
val bootstrapFileInputVersion: String by project
val gradleWrapperVersion: String by project

buildscript {
  val asciidoctorjPdfVersion: String by project
  val jrubyCompleteVersion: String by project
  val groovyVersion: String by project

  dependencies {
    classpath("org.asciidoctor:asciidoctorj-pdf:$asciidoctorjPdfVersion")
    classpath("org.jruby:jruby-complete:$jrubyCompleteVersion")
  }
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

allprojects {
  group = applicationGroup
  version = applicationVersion
  defaultTasks("clean", "build")
}

tasks.withType<Wrapper> {
  gradleVersion = gradleWrapperVersion
  distributionType = Wrapper.DistributionType.BIN
}

apply(from = "${project.rootDir}/gradle/ide.gradle")
apply(from = "${project.rootDir}/gradle/clean.gradle")
apply(from = "${project.rootDir}/gradle/subprojects.gradle")
apply(from = "${project.rootDir}/gradle/documentation.gradle")

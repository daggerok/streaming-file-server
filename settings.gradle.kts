pluginManagement {
  val spotbugsVersion: String by extra
  buildscript {
    repositories {
      gradlePluginPortal()
    }
    dependencies {
      classpath("gradle.plugin.com.github.spotbugs:spotbugs-gradle-plugin:$spotbugsVersion")
    }
  }
  repositories {
    gradlePluginPortal()
    mavenCentral()
    jcenter()
    maven(url = "https://repo.spring.io/milestone/")
    // maven(url = "https://repo.spring.io/snapshots/")
  }
  val querydslVersion: String by extra
  val versionsVersion: String by extra
  val springBootVersion: String by extra
  val asciidoctorVersion: String by extra
  val lombokPluginVersion: String by extra
  val dockerComposeVersion: String by extra
  val dependencyManagementVersion: String by extra
  // dependencies {
  //   classpath("gradle.plugin.com.github.spotbugs:spotbugs-gradle-plugin:$spotbugsVersion")
  // }
  plugins {
    id("com.github.spotbugs") version spotbugsVersion apply false
    id("org.springframework.boot") version springBootVersion apply false
    id("com.github.ben-manes.versions") version versionsVersion apply false
    id("io.franzbecker.gradle-lombok") version lombokPluginVersion apply false
    id("com.ewerk.gradle.plugins.querydsl") version querydslVersion apply false
    id("com.avast.gradle.docker-compose") version dockerComposeVersion apply false
    id("io.spring.dependency-management") version dependencyManagementVersion apply false
    id("org.asciidoctor.jvm.convert") version asciidoctorVersion apply false
    id("org.asciidoctor.jvm.gems") version asciidoctorVersion apply false
    id("org.asciidoctor.jvm.pdf") version asciidoctorVersion apply false
  }
}

val artifactId: String by extra
rootProject.name = artifactId

include(
    ":modules:libraries:props",
    ":modules:libraries:web-security",

    ":modules:apps:file-items-service",
    ":modules:apps:file-server",

    ":modules:docker:postgres",
    ":modules:docker:all"
)

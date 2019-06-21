plugins {
  java
}

subprojects {
  apply(plugin = "java")

  configure<JavaPluginExtension> {
    sourceCompatibility = Globals.javaVersion
    targetCompatibility = Globals.javaVersion
  }

  repositories {
    mavenCentral()
    maven(url = "https://repo.spring.io/milestone/")
    maven(url = "https://repo.spring.io/snapshot/")
    jcenter()
  }

  apply(plugin = "io.spring.dependency-management")
  dependencyManagement {
    imports {
      mavenBom("org.springframework.boot:spring-boot-dependencies:${Globals.springBootVersion}")
    }
  }

  this.dependencies {
    implementation("io.vavr:vavr:${Globals.vavrVersion}")
    testImplementation("org.powermock:powermock-module-junit4:${Globals.powermockitoVersion}")
    testImplementation("org.powermock:powermock-api-mockito2:${Globals.powermockitoVersion}")
    testImplementation("com.codeborne:selenide:${Globals.selenideVersion}")
  }

  apply(from = "${project.rootDir}/gradle/jgiven.gradle")
}

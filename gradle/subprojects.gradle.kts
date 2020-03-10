plugins {
  java
}

subprojects {
  apply(plugin = "java")

  configure<JavaPluginExtension> {
    sourceCompatibility = "$javaVersion"
    targetCompatibility = "$javaVersion"
  }

  repositories {
    mavenCentral()
    jcenter()
    maven(url = "https://repo.spring.io/milestone/")
    maven(url = "https://repo.spring.io/snapshot/")
  }

  apply(plugin = "io.spring.dependency-management")
  dependencyManagement {
    imports {
      mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion")
    }
  }

  this.dependencies {
    implementation("io.vavr:vavr:$vavrVersion")
    testImplementation("org.powermock:powermock-module-junit4:$powermockitoVersion")
    testImplementation("org.powermock:powermock-api-mockito2:$powermockitoVersion")
    testImplementation("com.codeborne:selenide:$selenideVersion")
  }

  apply(from = "$rootDir/gradle/jgiven.gradle")
}

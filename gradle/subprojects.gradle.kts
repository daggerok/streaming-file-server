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
    // maven(url = "https://repo.spring.io/snapshot/")
  }

  apply(plugin = "io.spring.dependency-management")
  dependencyManagement {
    imports {
      mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion")
    }
  }

  dependencies {
    implementation("io.vavr:vavr:$vavrVersion")
    testImplementation("com.codeborne:selenide:$selenideVersion")
  }
}

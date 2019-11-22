plugins {
  id("io.spring.dependency-management")
}

tasks {
  compileJava.get().dependsOn(processResources)
}

dependencies {
  compileOnly("org.springframework.boot:spring-boot-configuration-processor")
  testCompileOnly("org.springframework.boot:spring-boot-configuration-processor")
  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
  testAnnotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

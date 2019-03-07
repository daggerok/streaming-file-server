plugins {
  id("cn.bestwu.propdeps")
  id("cn.bestwu.propdeps-idea")
  id("cn.bestwu.propdeps-maven")
  id("cn.bestwu.propdeps-eclipse")
}

dependencies {
  compileOnly("org.springframework.boot:spring-boot-configuration-processor")
  testCompileOnly("org.springframework.boot:spring-boot-configuration-processor")
  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
  testAnnotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

val processResources = tasks.processResources
tasks.named("compileJava") {
  this.dependsOn(processResources)
}

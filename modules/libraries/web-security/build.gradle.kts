plugins {
  `java-library`
}

dependencies {
  api("org.springframework.boot:spring-boot-actuator-autoconfigure")
  api("org.springframework.security:spring-security-config")
  api("org.springframework.boot:spring-boot-starter-security")
  api("javax.servlet:javax.servlet-api")

  testImplementation("org.springframework.security:spring-security-test")
}

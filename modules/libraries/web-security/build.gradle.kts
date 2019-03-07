dependencies {
  compile("org.springframework.boot:spring-boot-actuator-autoconfigure")
  compile("org.springframework.security:spring-security-config")
  compile("org.springframework.boot:spring-boot-starter-security")
  compile("javax.servlet:javax.servlet-api")

  testCompile("org.springframework.security:spring-security-test")
}

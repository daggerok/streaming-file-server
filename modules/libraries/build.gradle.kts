subprojects {
  apply(plugin = "io.spring.dependency-management")

  dependencies {
    compile("org.springframework.boot:spring-boot-starter")
    testCompile("org.springframework.boot:spring-boot-starter-test")
  }
}

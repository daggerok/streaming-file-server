val hibernateJava8Version: String by project

dependencies {
  annotationProcessor(project(":modules:libraries:props"))
  implementation(project(":modules:libraries:props"))
  compileOnly(project(":modules:libraries:props"))

  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.hibernate:hibernate-java8:$hibernateJava8Version")

  runtimeOnly("org.postgresql:postgresql")
  runtimeOnly("com.h2database:h2")

  testImplementation("io.projectreactor:reactor-test")
}

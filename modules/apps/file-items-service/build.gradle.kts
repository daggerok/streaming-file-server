val hibernateJava8Version: String by project

dependencies {
  annotationProcessor(project(":modules:libraries:props"))
  implementation(project(":modules:libraries:props"))
  compileOnly(project(":modules:libraries:props"))

  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.hibernate:hibernate-java8:$hibernateJava8Version")

  runtime("org.postgresql:postgresql")
  runtime("com.h2database:h2")
  //runtime("org.springframework.boot:spring-boot-devtools")

  testImplementation("io.projectreactor:reactor-test")
}

/* // disabling using postgres by default
def postgersUp = tasks.findByPath(":modules:docker:postgres:composeUp")

[test, build, bootRun].each { task ->
  task.dependsOn assemble, postgersUp
  task.shouldRunAfter assemble, postgersUp
  ["all", "postgres"].each { id ->
    task.finalizedBy ":modules:docker:$id:${id}Down"
  }
}
*/

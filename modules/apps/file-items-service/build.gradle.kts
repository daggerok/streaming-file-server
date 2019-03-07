import Globals.globalHibernateJava8Version

dependencies {
  annotationProcessor(project(":modules:libraries:props"))
  implementation(project(":modules:libraries:props"))
  compileOnly(project(":modules:libraries:props"))

  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.hibernate:hibernate-java8:$globalHibernateJava8Version")

  runtime("org.postgresql:postgresql")
  runtime("com.h2database:h2")
  //runtime("org.springframework.boot:spring-boot-devtools")

  testImplementation("io.projectreactor:reactor-test")
}

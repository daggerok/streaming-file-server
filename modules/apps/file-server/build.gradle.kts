val jqueryVersion: String by project
val popperVersion: String by project
val commonsIoVersion: String by project
val bootstrapVersion: String by project
val fontAwesomeVersion: String by project
val bootstrapFileInputVersion: String by project

dependencies {
  arrayOf(":modules:libraries:props",
          ":modules:libraries:web-security")
      .map { project(it) }
      .forEach {
        annotationProcessor(it)
        implementation(it)
        compileOnly(it)
      }

  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-mustache")
  implementation("org.apache.commons:commons-io:$commonsIoVersion")

  // probably should be as a transitive dependency whenever it's needed...
  // implementation("org.webjars:jquery:$jqueryVersion")
  implementation("org.webjars.bower:bootstrap:$bootstrapVersion")
  implementation("org.webjars.bower:bootstrap-fileinput:$bootstrapFileInputVersion")
  implementation("org.webjars:font-awesome:$fontAwesomeVersion")
  implementation("org.webjars.npm:popper.js:$popperVersion")
}

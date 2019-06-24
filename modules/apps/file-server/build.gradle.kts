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
  implementation("org.apache.commons:commons-io:${Globals.commonsIoVersion}")

  implementation("org.webjars:jquery:${Globals.jqueryVersion}")
  implementation("org.webjars.bower:bootstrap:${Globals.bootstrapVersion}")
  implementation("org.webjars.bower:bootstrap-fileinput:${Globals.bootstrapFileInputVersion}")
  implementation("org.webjars:font-awesome:${Globals.fontAwesomeVersion}")
  implementation("org.webjars.npm:popper.js:${Globals.popperVersion}")
}

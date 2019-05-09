import org.gradle.api.JavaVersion

object Globals {
  object Project {
    const val version = "${project.version}"
    const val groupId = "com.github.daggerok"
    const val artifactId = "streaming-file-server"
  }

  val javaVersion = JavaVersion.VERSION_1_8

  const val vavrVersion = "${vavr.version}"
  const val slf4jVersion = "${slf4j.version}"
  const val jqueryVersion = "${jquery.version}"
  const val popperVersion = "${popper.version}"
  const val logbackVersion = "${logback.version}"
  const val lombokVersion = "${lombok.version}"
  const val jgivenVersion = "${jgiven.version}"
  const val selenideVersion = "${selenide.version}"
  const val bootstrapVersion = "${bootstrap.version}"
  const val commonsIoVersion = "${commons.io.version}"
  const val fontAwesomeVersion = "${font.awesome.version}"
  const val powermockitoVersion = "${powermockito.version}"
  const val bootstrapFileInputVersion = "${bootstrap.file.input.version}"
  const val springBootVersion = "${spring.boot.version}"
  const val hibernateJava8Version = "${hibernate.java8.version}"

  object Gradle {
    const val wrapperVersion = "${gradle.version}"

    object Plugin {
      const val lombokVersion = "3.0.0"
      const val querydslVersion = "1.0.10"
      const val propdepsVersion = "0.0.10"
      const val versionsVersion = "0.21.0"
      const val gitPublishVersion = "2.1.1"
      const val dockerComposeVersion = "0.7.1"  // any other higher version will fail on adoc task
      const val asciidoctorjConvertVersion = "1.5.9.1" // on windows my fail, use instead: 1.5.8.1
      const val dependencyManagementVersion = "1.0.7.RELEASE"
      const val toolVersion = "${tool.version}"
      const val spotbugsVersion = "${spotbugs.version}"
      const val jrubyCompleteVersion = "${jruby.complete.version}"
      const val asciidoctorjPdfVersion = "${asciidoctorj.pdf.version}"
    }
  }
}

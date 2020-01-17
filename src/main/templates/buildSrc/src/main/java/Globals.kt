import org.gradle.api.JavaVersion

object Globals {
  const val version = "${project.version}"
  const val groupId = "com.github.daggerok"
  const val artifactId = "streaming-file-server"

  val javaVersion = JavaVersion.VERSION_1_8

  const val vavrVersion = "${vavr.version}"
  const val jqueryVersion = "${jquery.version}"
  const val popperVersion = "${popper.version}"
  const val logbackVersion = "${logback.version}"
  const val lombokVersion = "${lombok.version}"
  const val jgivenVersion = "${jgiven.version}"
  const val selenideVersion = "${selenide.version}"
  const val postgresVersion = "${postgres.version}"
  const val bootstrapVersion = "${bootstrap.version}"
  const val commonsIoVersion = "${commons.io.version}"
  const val jacocoToolVersion = "${jacoco.tool.version}"
  const val fontAwesomeVersion = "${font.awesome.version}"
  const val powermockitoVersion = "${powermockito.version}"
  const val springBootVersion = "${spring.boot.version}"
  const val bootstrapFileInputVersion = "${bootstrap.file.input.version}"
  const val hibernateJava8Version = "${hibernate.java8.version}"

  const val wrapperVersion = "${gradle.version}"

  const val toolVersion = "${tool.version}"
  const val spotbugsVersion = "${spotbugs.version}"
  const val querydslVersion = "1.0.10"
  const val versionsVersion = "${versions.gradle.plugin}"
  const val lombokPluginVersion = "${lombok.plugin.version}"
  const val dependencyManagementVersion = "${io.spring.dependency-management}"
  const val dockerComposeVersion = "${docker.compose.gradle.plugin}"
  const val asciidoctorjPdfVersion = "${asciidoctorj.pdf.version}"
  const val asciidoctorjDiagramVersion = "${asciidoctorj.diagram.version}"
  const val asciidoctorjConvertVersion = "${asciidoctorj.convert.version}"
}

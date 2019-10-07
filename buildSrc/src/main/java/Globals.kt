import org.gradle.api.JavaVersion

object Globals {
  const val version = "4.3.28"
  const val groupId = "com.github.daggerok"
  const val artifactId = "streaming-file-server"

  val javaVersion = JavaVersion.VERSION_1_8

  const val vavrVersion = "0.10.2"
  const val slf4jVersion = "1.7.26"
  const val jqueryVersion = "3.4.1"
  const val popperVersion = "1.15.0"
  const val logbackVersion = "1.2.3"
  const val lombokVersion = "1.18.10"
  const val jgivenVersion = "0.18.2"
  const val selenideVersion = "5.3.1"
  const val postgresVersion = "42.2.8"
  const val bootstrapVersion = "4.3.1"
  const val commonsIoVersion = "1.3.2"
  const val jacocoToolVersion = "0.8.4"
  const val fontAwesomeVersion = "5.11.2"
  const val powermockitoVersion = "2.0.2"
  const val springBootVersion = "2.2.0.RC1"
  const val bootstrapFileInputVersion = "5.0.1"
  const val hibernateJava8Version = "5.4.6.Final"

  const val wrapperVersion = "5.6.2"

  const val toolVersion = "3.1.12"
  const val spotbugsVersion = "2.0.0"
  const val querydslVersion = "1.0.10"
  const val propdepsVersion = "0.0.10"
  const val versionsVersion = "0.25.0"
  const val gitPublishVersion = "2.1.1"
  const val lombokPluginVersion = "3.2.0"
  const val dockerComposeVersion = "0.7.1"  // any other higher version will fail on adoc task
  const val jrubyCompleteVersion = "9.2.5.0"
  const val asciidoctorjConvertVersion = "1.5.9.1" // on windows my fail, use instead: 1.5.8.1
  const val asciidoctorjPdfVersion = "1.5.0-alpha.16"
  const val dependencyManagementVersion = "1.0.8.RELEASE"
}

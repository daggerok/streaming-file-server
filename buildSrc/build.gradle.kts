plugins {
  idea
  eclipse
  `embedded-kotlin`
}

repositories {
  gradlePluginPortal()
}

idea {
  module {
    isDownloadJavadoc = false
    isDownloadSources = true
  }
}

eclipse {
  classpath {
    isDownloadJavadoc = false
    isDownloadSources = true
  }
}

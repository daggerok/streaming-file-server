plugins {
  idea
  eclipse
  `kotlin-dsl`
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

kotlinDslPluginOptions {
  experimentalWarning.set(false)
}

plugins {
  idea
  `kotlin-dsl`
}

repositories {
  gradlePluginPortal()
}

idea {
  module {
    isDownloadJavadoc = false
    isDownloadSources = false
  }
}

kotlinDslPluginOptions {
  experimentalWarning.set(false)
}

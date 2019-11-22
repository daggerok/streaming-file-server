plugins {
  idea
  `kotlin-dsl`
  `embedded-kotlin`
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

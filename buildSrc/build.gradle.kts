plugins {
  `kotlin-dsl`
}

repositories {
  mavenCentral()
}

configure<KotlinDslPluginOptions> {
  experimentalWarning.set(false)
}

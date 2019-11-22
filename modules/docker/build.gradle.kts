subprojects {
    apply<com.avast.gradle.dockercompose.DockerComposePlugin>()

    val root = rootDir.absolutePath.replace("\\\\", "/")
    val debugEnabledProperty = project.findProperty("debug") ?: "false"
    val isDebugEnabled = debugEnabledProperty != "false"

    configure<com.avast.gradle.dockercompose.ComposeExtension> {
        useComposeFiles = listOf("${root}/modules/docker/${name}/docker-compose.yml")
        removeImages = com.avast.gradle.dockercompose.RemoveImages.Local
        captureContainersOutput = isDebugEnabled
        projectName = parent?.name ?: "docker"
        removeContainers = true
        waitForTcpPorts = false
        stopContainers = true
        removeVolumes = true
        removeOrphans = true
        forceRecreate = true
    }

    tasks {
        create("${name}Up") {
            if (name.contains("app")) dependsOn(assemble.get())
            dependsOn(named("composeUp").get().path)
        }
        create("${name}Down") {
            finalizedBy(named("composeDown").get().path)
        }
    }
}

subprojects {
    apply<com.avast.gradle.dockercompose.DockerComposePlugin>()

    // fucking windows paths... replace all: '\\' -> '/'
    val root = rootDir.absolutePath.replace("\\\\", "/")

    // gradle -Pdebug
    val isDebugEnabled = this.hasProperty("debug")

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
        //executable = '/path/to/docker-compose'
        //dockerExecutable = '/path/to/docker'
        //captureContainersOutput = true
        //captureContainersOutput = false
        //captureContainersOutputToFile = '/path/to/logFile'
    }
}

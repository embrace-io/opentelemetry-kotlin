tasks.register<BuildIosAppTask>("buildIosApp") {
    iosProject = "iosApp.xcodeproj"
    iosScheme = "iosApp"
    iosConfiguration = "Debug"
    iosSimulatorName = "iPhone 16 Pro"
    iosSimulatorOS = "18.5"
}

abstract class BuildIosAppTask @Inject constructor(
    private val execOperations: ExecOperations
) : Exec() {

    override fun setDependsOn(dependsOn: Iterable<*>) {
        super.setDependsOn(dependsOn.plus("embedAndSignAppleFrameworkForXcode"))
    }

    @Input
    lateinit var iosProject: String

    @Input
    lateinit var iosScheme: String

    @Input
    lateinit var iosConfiguration: String

    @Input
    lateinit var iosSimulatorName: String

    @Input
    lateinit var iosSimulatorOS: String

    override fun exec() {
        execOperations.exec {
            commandLine = listOf(
                "xcodebuild",
                "-project", iosProject,
                "-scheme", iosScheme,
                "-configuration", iosConfiguration,
                "-sdk", "iphonesimulator",
                "-destination", "platform=iOS Simulator,name=$iosSimulatorName,OS=$iosSimulatorOS",
                "build",
            )
        }
    }
}

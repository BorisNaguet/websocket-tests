package dw.websocket.gatling


/**
  * Created by A160420 on 09/06/2016.
  */
object GatlingApp extends App {
import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder
  import dw.websocket.gatling.BasicSimulation

    val props = new GatlingPropertiesBuilder
    props.dataDirectory(IDEPathHelper.dataDirectory.toString)
    props.resultsDirectory(IDEPathHelper.resultsDirectory.toString)
    props.bodiesDirectory(IDEPathHelper.bodiesDirectory.toString)
    props.binariesDirectory(IDEPathHelper.mavenBinariesDirectory.toString)
    props.simulationClass("dw.websocket.gatling.BasicSimulation")
    props.mute()

    Gatling.fromMap(props.build)

}

object IDEPathHelper {
  import java.nio.file.Path
  import io.gatling.commons.util.PathHelper._

//  val gatlingConfUrl: Path = getClass.getClassLoader.getResource("gatling.conf").toURI
//  val projectRootDir = gatlingConfUrl.ancestor(3)
  val projectRootDir: Path = "."

  val mavenSourcesDirectory = projectRootDir / "src" / "test" / "scala"
  val mavenResourcesDirectory = projectRootDir / "src" / "test" / "resources"
  val mavenTargetDirectory = projectRootDir / "target"
  val mavenBinariesDirectory = mavenTargetDirectory / "test-classes"

  val dataDirectory = mavenResourcesDirectory / "data"
  val bodiesDirectory = mavenResourcesDirectory / "bodies"

  val recorderOutputDirectory = mavenSourcesDirectory
  val resultsDirectory = mavenTargetDirectory / "results"

  val recorderConfigFile = mavenResourcesDirectory / "recorder.conf"
}

import sbt._

object BuildElectric extends Build {

  import CoreSettings._

  lazy val core = (project in file("core")).
    settings(coreSettings: _*)


  lazy val electric = (project in file("electric")).
    settings(coreSettings ++ electricSettings: _*) dependsOn (core)


  lazy val electricexamples = (project in file("electricexamples")).
    settings(coreSettings ++ electricJobSettings ++ sparkAssemblySettings: _*) dependsOn (electric)


}




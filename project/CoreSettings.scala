import sbt.Keys._
import sbt._
import sbtassembly.AssemblyKeys._

object CoreSettings {


  val sparkVersion = "1.3.1"
  val currentScalaVersion ="2.10.4"
  val currentVersion="0.0.2"
  val organizationName="com.recipegrace.electric"

  // sbt-assembly settings for building a fat jar
  lazy val sparkAssemblySettings = Seq(

    // Slightly cleaner jar name
    assemblyJarName in assembly := {
      name.value + "-" + version.value + ".jar"
    },

    // Drop these jars
    assemblyExcludedJars in assembly <<= (fullClasspath in assembly) map { cp =>
      val excludes = Set(
        "jsp-api-2.1-6.1.14.jar",
        "jsp-2.1-6.1.14.jar",
        "jasper-compiler-5.5.12.jar",
        "commons-beanutils-core-1.8.0.jar",
        "commons-beanutils-1.7.0.jar",
        "servlet-api-2.5-20081211.jar",
        "servlet-api-2.5.jar",
        "scala-xml-2.11.0-M4.jar",
        "jsr311-api-1.1.1.jar"
      )
      cp filter { jar => excludes(jar.data.getName) }
    }

  )
  val coreSettings = Seq(
    version := currentVersion,
    scalaVersion := currentScalaVersion,
    organization := organizationName,
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "2.2.5",
      "org.slf4j" % "slf4j-simple" % "1.7.12",
      "net.sf.jopt-simple" % "jopt-simple" % "4.9",
  "com.typesafe.scala-logging" % "scala-logging-slf4j_2.10" % "2.1.2"),
    resolvers ++= Resolvers.allResolvers)

  val electricSettings = Seq(
    name := "Electric",
    publishArtifact in Test := true,
    libraryDependencies ++= Seq(
      Libraries.sparkCore
    )
  )

  val electricJobSettings = Seq(
    name := "ElectricExamples",
    test in assembly := {},
    libraryDependencies ++= Seq(

      Libraries.sparkCore)
  )
  


  object Libraries {
    val sparkCore = "org.apache.spark" %% "spark-core" % sparkVersion % "provided"
    val sparkMllib = "org.apache.spark" %% "spark-mllib" % sparkVersion % "provided"
    val sparkSql = "org.apache.spark" %% "spark-sql" % sparkVersion % "provided"

    // Add additional libraries from mvnrepository.com (SBT syntax) here...
  }
}

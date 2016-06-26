import sbt.Keys._
import sbt._
import sbtassembly.AssemblyKeys._

object CoreSettings {

  val sparkVersion = "1.6.1"
  val currentScalaVersion = "2.10.6"
  val currentVersion = "0.0.3-SNAPSHOT"
  val organizationName = "com.recipegrace"

  // sbt-assembly settings for building a fat jar
  lazy val sparkAssemblySettings = Seq(

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
    crossScalaVersions := Seq("2.10.6","2.11.5"),
    organization := organizationName,
    test in assembly := {},
    parallelExecution in Test := false,
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "2.2.5",
      "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2",
      "org.apache.commons" % "commons-lang3" % "3.4",
      "commons-io" % "commons-io" % "2.4",
      "com.thoughtworks.paranamer" % "paranamer-parent" % "2.4.1" pomOnly(),
      "info.debatty" % "java-string-similarity" % "0.13"
    ),
    publishTo := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value) Some(Resolvers.ossSnapshots)
      else Some(Resolvers.ossStaging)
     },
    credentials += Credentials("sonatype.sbt"),
    pomIncludeRepository := { _ => false },
    pomExtra := (
  <url>http://recipegrace.com/recipegrace</url>
  <licenses>
    <license>
      <name>BSD-style</name>
      <url>http://www.opensource.org/licenses/bsd-license.php</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:recipegrace/BigLibrary.git</url>
    <connection>scm:git:git@github.com:recipegrace/BigLibrary.git</connection>
  </scm>
  <developers>
    <developer>
      <id>feroshjacob</id>
      <name>Ferosh Jacob</name>
      <url>http://www.feroshjacob.com</url>
    </developer>
  </developers>),
    resolvers ++= Resolvers.allResolvers)

  val electricSettings = Seq(
    name := "Electric",
    publishArtifact in Test := true,
    parallelExecution in Test := false,
    libraryDependencies ++= Seq(
      Libraries.sparkCore
    )
  )

  val electricJobSettings = Seq(
    name := "ElectricExamples",
    test in assembly := {},
    libraryDependencies ++= Seq(
      "com.cybozu.labs" % "langdetect" % "1.1-20120112",
      Libraries.sparkCore)
  )


  object Libraries {
    val sparkCore = "org.apache.spark" %% "spark-core" % sparkVersion % "provided"
    val sparkMllib = "org.apache.spark" %% "spark-mllib" % sparkVersion % "provided"
    val sparkSql = "org.apache.spark" %% "spark-sql" % sparkVersion % "provided"
  }

}

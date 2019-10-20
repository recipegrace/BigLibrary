val mvnrepository = "MVN Repo" at "http://central.maven.org/maven2/"
val ossSnapshots = "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
val ossStaging = "Sonatype OSS Snapshots" at "https://oss.sonatype.org/service/local/staging/deploy/maven2"
val allResolvers = Seq(mvnrepository)

val sparkVersion = "2.4.0"
val gcsVersion = "1.40.0"
val organizationName = "com.recipegrace"
lazy val scala212 = "2.12.8"
lazy val scala211 = "2.11.12"
lazy val supportedScalaVersions = List(scala212, scala211)

val username = System.getenv().get("SONATYPE_USERNAME")

val password = System.getenv().get("SONATYPE_PASSWORD")

val passphrase = System.getenv().get("PGP_PASSPHRASE") match {
  case x: String => x
  case null      => ""
}

// sbt-assembly settings for building a fat jar
lazy val sparkAssemblySettings = Seq(
  assemblyJarName in assembly := {
    name.value + "-" + version.value + ".jar"
  },
  // Drop these jars
  assemblyExcludedJars in assembly := {
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
    val cp = (fullClasspath in assembly).value
    cp filter { jar =>
      excludes(jar.data.getName)
    }
  }
)
lazy val assemblySettings = Seq(
  assemblyJarName in assembly := {
    name.value + "-" + version.value + ".jar"
  }
)
val coreSettings = Seq(
  organization := organizationName,
  pgpPassphrase := Some( System.getenv().get("PGP_PASSPHRASE").toCharArray),
  pgpSecretRing := file("/home/travis/.gnupg/secring.gpg/secring.gpg"),
  pgpPublicRing := file("/home/travis/.gnupg/secring.gpg/pubring.gpg"),
  test in assembly := {},
  parallelExecution in Test := false,
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.0.1",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
    "ch.qos.logback" % "logback-classic" % "1.1.7",
    "org.apache.commons" % "commons-lang3" % "3.4",
    "commons-io" % "commons-io" % "2.4",
    "com.thoughtworks.paranamer" % "paranamer-parent" % "2.4.1" pomOnly (),
    "com.thoughtworks.paranamer" % "paranamer" % "2.8",
    "info.debatty" % "java-string-similarity" % "0.13",
    "org.slf4j" % "slf4j-log4j12" % "1.7.10" % "test"
  ),
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value) Some(ossSnapshots)
    else Some(ossStaging)
  },
  credentials += Credentials(
    "Sonatype Nexus Repository Manager",
    "oss.sonatype.org",
    username,
    password
  ),
  pomIncludeRepository := { _ =>
    false
  },
  pomExtra := (<url>http://recipegrace.com/recipegrace</url>
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
            <url>https://feroshjacob.github.io</url>
          </developer>
        </developers>),
  resolvers ++= allResolvers
)

val electricSettings = Seq(
  name := "Electric",
  publishArtifact in Test := true,
  libraryDependencies ++= Seq(
    "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
    "org.apache.spark" %% "spark-mllib" % sparkVersion % "provided"
  )
)

val gasSettings = Seq(
  name := "Gas",
  publishArtifact in Test := true,
  libraryDependencies ++= Seq(
    "com.google.cloud" % "google-cloud-storage" % gcsVersion
  )
)
val noPublishSettings = Seq(crossScalaVersions := Nil, skip in publish := true)

val publishSettings = Seq(crossScalaVersions := supportedScalaVersions)
val gasJobSettings = Seq(
  name := "GasExamples",
  publishArtifact in Test := false,
  libraryDependencies ++= Seq(
    "com.google.cloud" % "google-cloud-storage" % gcsVersion
  )
)
val electricJobSettings = Seq(
  name := "ElectricExamples",
  test in assembly := {},
  libraryDependencies ++= Seq(
    "com.cybozu.labs" % "langdetect" % "1.1-20120112",
    "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
    "org.apache.spark" %% "spark-mllib" % sparkVersion % "provided"
  )
)

lazy val core =
  (project in file("core")).settings(coreSettings ++ publishSettings: _*)

lazy val electric = (project in file("electric"))
  .settings(coreSettings ++ electricSettings ++ publishSettings: _*) dependsOn (core)

lazy val electricexamples = (project in file("electricexamples")).settings(
  coreSettings ++ electricJobSettings ++ noPublishSettings: _*
) dependsOn (electric)

lazy val gas = (project in file("gas"))
  .settings(coreSettings ++ gasSettings ++ publishSettings: _*) dependsOn (core)

lazy val gasexamples = (project in file("gasexamples")).settings(
  coreSettings ++ gasJobSettings ++ noPublishSettings: _*
) dependsOn (gas)

lazy val biglibrary = (project in file("."))
  .settings(
    coreSettings ++ noPublishSettings: _*
  )
  .aggregate(core, electric, gas)

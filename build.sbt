
  val mvnrepository = "MVN Repo" at "http://central.maven.org/maven2/"
  val ossSnapshots =  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  val ossStaging =  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/service/local/staging/deploy/maven2"
  val allResolvers = Seq(mvnrepository)

  val sparkVersion = "2.3.1"
  val gcsVersion = "1.40.0"
  val currentScalaVersion = "2.11.6"
  val organizationName = "com.recipegrace"

  val username = System.getenv().get("SONATYPE_USERNAME")

  val password = System.getenv().get("SONATYPE_PASSWORD")

  val passphrase = System.getenv().get("PGP_PASSPHRASE") match {
      case x:String => x
      case null => ""
      }

  // sbt-assembly settings for building a fat jar
  lazy val sparkAssemblySettings = Seq(

    assemblyJarName in assembly := {
      name.value + "-" +version.value + ".jar"
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
  lazy val assemblySettings = Seq(

    assemblyJarName in assembly := {
      name.value + "-" +version.value + ".jar"
    }
  )
  val coreSettings = Seq(

    pgpPassphrase := Some( passphrase.toCharArray),
    pgpSecretRing := file("local.secring.gpg"),
    pgpPublicRing := file("local.pubring.gpg"),
    scalaVersion  := currentScalaVersion,
    //crossScalaVersions := Seq("2.10.6", "2.11.5"),
    organization := organizationName,
    test in assembly := {},
    parallelExecution in Test := false,
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "2.2.5",
      "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2",
      "org.apache.commons" % "commons-lang3" % "3.4",
      "commons-io" % "commons-io" % "2.4",
      "com.thoughtworks.paranamer" % "paranamer-parent" % "2.4.1" pomOnly(),
       "com.thoughtworks.paranamer" % "paranamer" % "2.8",
      "info.debatty" % "java-string-similarity" % "0.13",
      "org.slf4j" % "slf4j-log4j12" % "1.7.10" % "test"
    ),
    publishTo := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value) Some(ossSnapshots)
      else Some(ossStaging)
    },
    credentials += Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", username, password),
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
            <url>https://feroshjacob.github.io</url>
          </developer>
        </developers>),
    resolvers ++= allResolvers)

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
    publishArtifact in Test :=true,
    libraryDependencies ++= Seq(
    "com.google.cloud" % "google-cloud-storage" % gcsVersion 
    )
  )
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


  
  lazy val core = (project in file("core")).
    settings(coreSettings: _*)


  lazy val electric = (project in file("electric")).
    settings(coreSettings ++ electricSettings: _*) dependsOn (core)


  lazy val electricexamples = (project in file("electricexamples")).
    settings(coreSettings ++ electricJobSettings ++ sparkAssemblySettings: _*) dependsOn (electric)

  lazy val gas = (project in file("gas")).
    settings(coreSettings ++ gasSettings: _*) dependsOn (core)


  lazy val gasexamples = (project in file("gasexamples")).
    settings(coreSettings ++ gasJobSettings ++assemblySettings : _*) dependsOn (gas)


  lazy val biglibrary = (project in file(".")).
    settings(coreSettings: _*) aggregate (core,electric,gas)





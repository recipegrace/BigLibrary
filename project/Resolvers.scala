
import sbt._

object Resolvers {
  val mvnrepository = "MVN Repo" at "http://central.maven.org/maven2/"
  val recipegrace = "Recipegrace repo" at "http://recipegrace.com/nexus/content/repositories/releases/"
  val recipegraceSnapshots = "Recipegrace snapshots" at "http://recipegrace.com/nexus/content/repositories/snapshots/"
  val hdNexusSnapshots = "Gitlab Nexus snapshots" at "https://nexus.hdtechlab.com/nexus/content/repositories/snapshots"
  val ossSnapshots =  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  val ossStaging =  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/staging"

  /* val typesafe = "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
   val sonatype = "Sonatype Release" at "https://oss.sonatype.org/content/repositories/releases"
   val conjars = "Concurrent Maven Repo" at "http://conjars.org/repo"
   val cloudera = "cloudera" at "https://repository.cloudera.com/artifactory/cloudera-repos/"
 */
  val allResolvers = Seq(mvnrepository)

}

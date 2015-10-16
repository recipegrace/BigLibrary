
import sbt._

object Resolvers {
  val mvnrepository = "MVN Repo" at "http://central.maven.org/maven2/"
  /* val typesafe = "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
   val sonatype = "Sonatype Release" at "https://oss.sonatype.org/content/repositories/releases"
   val conjars = "Concurrent Maven Repo" at "http://conjars.org/repo"
   val cloudera = "cloudera" at "https://repository.cloudera.com/artifactory/cloudera-repos/"
 */
  val allResolvers = Seq(mvnrepository)

}

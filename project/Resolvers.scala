
import sbt._

object Resolvers {
  val typesafe = "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
  val sonatype = "Sonatype Release" at "https://oss.sonatype.org/content/repositories/releases"
  val mvnrepository = "MVN Repo" at "http://mvnrepository.com/artifact"
  val conjars = "Concurrent Maven Repo" at "http://conjars.org/repo"
 val publishTo="Local Maven Repository" at ""+Path.userHome.asFile.toURI.toURL+".m2/repository/"

  val cloudera = "cloudera" at "https://repository.cloudera.com/artifactory/cloudera-repos/"

  val allResolvers = Seq(typesafe, sonatype, mvnrepository, conjars, publishTo, cloudera)

}

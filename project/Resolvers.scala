
import sbt._

object Resolvers {
  val mvnrepository = "MVN Repo" at "http://central.maven.org/maven2/"
  val ossSnapshots =  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  val ossStaging =  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/service/local/staging/deploy/maven2"

  val allResolvers = Seq(mvnrepository)

}

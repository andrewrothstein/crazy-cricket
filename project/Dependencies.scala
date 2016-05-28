import sbt._

object Dependencies {

  object Versions {
    val kafkaVer = "0.9.0.1"
    val scoptVer = "3.4.0"
    val scalaHttpVer = "2.3.0"

  }

  import Versions._

  val kafkaDeps = Seq(
    "org.apache.kafka" % "kafka-clients" % kafkaVer
  )

  val coreDeps = Seq(
    "com.github.scopt" %% "scopt" % scoptVer,
    "org.scalaj" %% "scalaj-http" % scalaHttpVer
  )

  val allDeps =
    coreDeps ++
      kafkaDeps
}
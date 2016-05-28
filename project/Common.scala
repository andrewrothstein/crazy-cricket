import sbt.Keys._

object Common {

  val commonSettings = Seq(
    name := "crazy-cricket",
    version := "0.1",
    scalaVersion := "2.11.7"
  )
}
import Common._
import Dependencies._
import sbtprotobuf.{ProtobufPlugin=>PB}

PB.protobufSettings

javaSource in PB.protobufConfig <<= (sourceDirectory in Compile)(_ / "generated")

lazy val root = (project in file(".")).
  settings(commonSettings:_*).
  settings(libraryDependencies ++= allDeps)
ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.14"

libraryDependencies ++= Seq(
  "org.apache.logging.log4j" % "log4j-api" % "2.14.1",
  "org.apache.logging.log4j" % "log4j-core" % "2.14.1"
)


libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.28" // Use the appropriate version


lazy val root = (project in file("."))
  .settings(
    name := "Project"
  )

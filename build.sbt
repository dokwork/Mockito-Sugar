lazy val mockitoSugar = (project in file("."))
  .settings(
    name := "mockito-sugar",
    organization := "ru.dokwork",
    scalaVersion := "2.11.11",
    crossScalaVersions := Seq("2.11.11", "2.12.4"),
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
    libraryDependencies ++= Seq(
      "org.mockito" % "mockito-core" % "2.12.0" % "provided",
      "org.scalatest" %% "scalatest" % "3.0.4" % "provided",
      // tests:
      "org.scalatest" %% "scalatest" % "3.0.0" % "test",
      "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
    ),
    releaseCrossBuild := true,
    licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
    pomExtra :=
      <developers>
        <developer>
          <id>dokwork</id>
          <name>Vladimir Popov</name>
          <url>http://dokwork.ru</url>
        </developer>
      </developers>
  )

name := "scalatags-rx-example"
scalaVersion := "2.11.8"

libraryDependencies += "com.timushev" %%% "scalatags-rx" % "0.3.0"

enablePlugins(ScalaJSPlugin, WorkbenchPlugin)
scalaJSUseMainModuleInitializer := true
addCommandAlias("dev", "~fastOptJS")
refreshBrowsers <<= refreshBrowsers.triggeredBy(fastOptJS in Compile)

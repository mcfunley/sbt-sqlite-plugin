import sbt._


class SqlitePlugin(info : ProjectInfo) extends PluginProject(info) {
  override def outputDirectoryName = "build"
  override def mainScalaSourcePath = "src"
  override def mainResourcesPath   = "lib"
  override def testScalaSourcePath = "tests"

  
}

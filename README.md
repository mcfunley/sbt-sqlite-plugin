# sbt-sqlite-plugin
A [simple-build-tool](http://code.google.com/p/simple-build-tool/) plugin that adds support for sqlite fixtures. 

By Dan McKinley - dan@etsy.com - [http://mcfunley.com](http://mcfunley.com)


## Installation

To use, create a file in project/plugins with the following contents:

<pre>
import sbt._

class Plugins(info: ProjectInfo) extends PluginDefinition(info) {
  val sqlite = "com.etsy" % "sbt-sqlite-plugin" % "1.0"
}
</pre>


## Version History

### Version 1.0 - 07/08/2010
Initial release

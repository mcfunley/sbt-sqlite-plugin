package com.etsy.sbt

import _root_.sbt._
import Process._


trait SqliteDatabase extends BasicScalaProject {
  self : MavenStyleScalaPaths => 

  def sqliteFixturesPath = testScalaSourcePath / "fixtures.sql"
  def sqliteDatabasePath = testScalaSourcePath / "fixtures.sqlite"

  lazy val sqliteDatabase = task { 
    sqliteDatabaseAction 
  } describedAs("Creates a test (SQLite) database.")


  def sqliteDatabaseAction = {
    if(testDBMissing || fixturesNewer || testDBTooSmall) {
      cleanTestDB
      createTestDB
    } else {
      log.info("Test database up to date.")
    }
    None
  } 

  override def testListeners = super.testListeners ++ Seq(new TestsListener {
    def doInit = sqliteDatabaseAction
    def doComplete(finalResult : Result.Value) = {}
    def startGroup(name: String) {}
    def testEvent(event: TestEvent) {}
    def endGroup(name: String, t: Throwable) {}
    def endGroup(name: String, result: Result.Value) {}
  })
  
  private def fixturesNewer = {
    if(sqliteFixturesPath newerThan sqliteDatabasePath) {
      log.info("Database fixtures changed, recreating database.")
      true
    } else {
      false
    }
  }


  private def testDBTooSmall = {
    if(sqliteDatabasePath.asFile.length < 50) {
      log.info("Test database suspiciously small, recreating.")
      true
    } else {
      false
    }
  }


  private def testDBMissing = {
    if(!sqliteDatabasePath.exists) {
      log.info("Test database does not exist, recreating.")
      true
    } else false
  }

  private def cleanTestDB = sqliteDatabasePath.asFile.delete

  private def cp(p : Path) = p.asFile.getCanonicalPath

  private def createTestDB = 
    ("sqlite3 " + cp(sqliteDatabasePath)) #< sqliteFixturesPath.asFile !

}

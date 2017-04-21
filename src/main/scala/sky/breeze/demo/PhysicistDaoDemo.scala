package sky.breeze.demo

import sky.utils.sql.SqlUtils

object PhysicistDaoDemo extends App {

  val physicists = SqlUtils.usingConnection("test") {
    connection => PhysicistDao.readAll(connection)
  }

  // physicists is a Vector[Physicist] instance.
  physicists.foreach { println }
}

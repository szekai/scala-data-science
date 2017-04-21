package sky.breeze.demo

import java.sql.{Connection, ResultSet}

import sky.utils.sql.Implicits._
import sky.utils.sql.{Gender, SqlUtils} // implicit conversions

object PhysicistDao {

  /* Helper method for reading a single row */
  private def readFromResultSet(results:ResultSet):Physicist = {
    Physicist(
      results.read[String]("name"),
      results.read[Gender.Value]("gender")
    )
  }

  /* Read the entire 'physicists' table. */
  def readAll(connection:Connection):Vector[Physicist] = {
    connection.withQuery("SELECT * FROM physicists") {
      results =>
        val resultStream = SqlUtils.stream(results)
        resultStream.map(readFromResultSet).toVector
    }
  }
}

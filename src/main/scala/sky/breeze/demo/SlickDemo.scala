package sky.breeze.demo

import java.sql.Date
import java.text.SimpleDateFormat

import slick.jdbc.H2Profile.api._
import slick.jdbc.meta.MTable

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

/**
  * Created by 1531048 on 4/25/2017.
  */
object SlickDemo {
  val db = Database.forConfig("h2mem")
  // The query interface for the Suppliers table
  val transactions: TableQuery[Transactions] = TableQuery[Transactions]

  def createTable: Future[Unit] = db.run(DBIO.seq(
    // Create the schema by combining the DDLs for the Suppliers and Coffees
    // tables using the query interfaces
    transactions.schema.create))

 private def insertData(table: TableQuery[Transactions], data: Transaction): Unit ={
   db.run(table += data )
 }
  def insertFecData = {
        val fecData = FECData.loadOhio
//    val transactionBatches = fecData.transactions.grouped(100000)
//    for (batch <- transactionBatches) {
//      transactions ++= batch.toList
//    }
//
//    db.run(transactions.result)
        fecData.transactions foreach(insertData(transactions , _))

  }


  def queryData(f: Any => Unit ) = {
    db.run(transactions.take(5).result).map(f)
  }

  def queryTotalDonations(f: Any => Unit ) = {
    val action = transactions.result
     db.run(action).map{
      posts => {
        val grouped = posts.groupBy(_.candidate)
        val aggregated = grouped.map {
          case (candidate, group) => candidate -> group.map(_.amount).sum
        }
        f(aggregated.toList.toMap)
      }
    }
  }

  def main(args:Array[String]) {
    createTable
//    val f =
//      createTable.onComplete{
//      case Success(id) => insertFecData
//      case _ => print("fail")
//    }
      insertFecData
//    db.close()
//    val g = queryData { println }
   val g = queryTotalDonations { println }
//    Await.result(f, Duration.Inf)

    Await.result(g, Duration.Inf)
  }
}

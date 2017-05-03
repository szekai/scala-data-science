package sky.breeze.demo

import java.sql.Date

import slick.jdbc.H2Profile.api._
import slick.lifted.ProvenShape

// A Suppliers table with 6 columns: id, name, street, city, state, zip
class Transactions(tag: Tag)
  extends Table[Transaction](tag, "Transactions") {

  // This is the primary key column:
  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def candidate: Rep[String] = column[String]("candidate")
  def contributor: Rep[String] = column[String]("contributor")
  def contributorState: Rep[String] = column[String]("contributorState", O.SqlType("VARCHAR(2)"))
  def contributorOccupation: Rep[Option[String]] = column[Option[String]]("contributor_occupation")
  def amount: Rep[Long] = column[Long]("amount")
  def date:Rep[Date] = column[Date]("date")

  // Every table needs a * projection with the same type as the table's type parameter
  def * :ProvenShape[Transaction] = (id.?, candidate, contributor, contributorState, contributorOccupation, amount, date)<> (
      Transaction.tupled, Transaction.unapply)
}

package model.db

import model.User
import slick.jdbc.MySQLProfile.api._
import slick.lifted.Tag

class UserTable(tag: Tag) extends Table[User](tag, "users") {
  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def username: Rep[String] = column[String]("name")
  def email: Rep[String] = column[String]("email")
  def passwd: Rep[String] = column[String]("passwd")

  def * = (id, username, email, passwd) <> (User.tupled, User.unapply)
}


package model.db

import model.Task
import slick.jdbc.MySQLProfile.api._
import slick.lifted.Tag

class TaskTable(tag: Tag) extends Table[Task](tag, "tasks") {
  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def title: Rep[String] = column[String]("title")
  def text: Rep[String] = column[String]("text")
  def done: Rep[Boolean] = column[Boolean]("done")
  def user: Rep[Int] = column[Int]("user_id")
  def foreign = foreignKey("user_id", user, TableQuery[UserTable])(_.id)

  def * = (id, title, text, done, user) <> (Task.tupled, Task.unapply)
} 
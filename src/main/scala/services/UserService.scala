package services

import com.sun.xml.internal.bind.v2.TODO
import model._
import model.db.UserTable
import slick.lifted.TableQuery
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserService(db: Database, val users: TableQuery[UserTable]) {

  def createUser(user: User) = db.run(users += user)

  def deleteUser(user: User) = db.run(users.filter(_.id === user.id).delete)

  def findUserByEmail(email: String): Future[Option[User]] =
    db.run(users.filter(_.email === email).result.headOption)

  def findUserById(id: Int): Future[Option[User]] = db.run(users.filter(_.id === id).result.headOption)

  def findUserByName(name: String): Future[Option[User]] = db.run(users.filter(_.username === name).result.headOption)

}

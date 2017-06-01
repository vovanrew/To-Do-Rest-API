package services

import model._
import model.db.UserTable
import slick.lifted.TableQuery
import slick.jdbc.MySQLProfile.api._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserService(db: Database, val users: TableQuery[UserTable]) {

  def createUser(user: User): Future[Option[String]] = {
    db.run(users.filter(_.email === user.email).result).flatMap {
      case seq => {
        if (seq.isEmpty)
          Future.successful(None)
        else {
          db.run(users += user)
          Future.successful(Some("Success"))
        }
      }
    }
  }

  def updateUserAcountById(id: Int, newUser: User) = {
    db.run(users.filter(_.id === id)
      .map(user => (user.username, user.email, user.passwd))
      .update((newUser.username, newUser.email, newUser.passwd)))
  }

  def updateUserAcountByEmail(email: String, newUser: User) = {
    db.run(users.filter(_.email === email)
      .map(user => (user.username, user.email, user.passwd))
      .update((newUser.username, newUser.email, newUser.passwd)))
  }

  def deleteUser(user: User) = db.run(users.filter(_.id === user.id).delete)

  def findUserByEmail(email: String): Future[Option[User]] =
    db.run(users.filter(_.email === email).result.headOption)

  def findUserById(id: Int): Future[Option[User]] = db.run(users.filter(_.id === id).result.headOption)

  def findUserByName(name: String): Future[Option[User]] = db.run(users.filter(_.username === name).result.headOption)

}

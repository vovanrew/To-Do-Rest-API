package services

import model.{Task, User}
import utils.RespStatUtil._
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by vovanrew on 14.05.17.
  */
class AuthServise(db: Database, userService: UserService, taskService: TaskService)
                 (implicit executionContext: ExecutionContext) {

  def signUp(user: User): Future[Option[String]] = {
    userService.findUserByEmail(user.email).flatMap {
      case Some(user) =>
        Future.successful(Some("Failure"))
      case None => {
        userService.createUser(user)
        Future.successful(Some("Success"))
      }
    }
  }

  def signIn(login: String, password: String): Future[Option[Seq[Task]]] = {
    db.run(userService.users.filter(_.email === login).result).flatMap { users =>
      users.find(_.passwd == password) match {
        case Some(user) => db.run(taskService.tasks.filter(_.user === user.id).result).map {
          case seq: Seq[Task] => Some(seq)
        }
        case None => Future.successful(None)
      }
    }
  }
}

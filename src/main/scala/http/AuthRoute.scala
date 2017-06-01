package http

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import model.{LoginPassword, Task, User}
import services.AuthServise
import utils.RespStatUtil._
import utils.JsonFormaters
import spray.json._

import scala.concurrent.{ExecutionContext, Future}

class AuthRoute(val authServise: AuthServise)(implicit executionContext: ExecutionContext) extends JsonFormaters {

  import authServise._

  val route: Route =
    put {
      path("signUp") {
        entity(as[User]) { user =>
          val res: Future[Option[String]] = signUp(user)
          onSuccess(res) {
            case Some(str) if str == "Failure" =>
              complete(failureWithExplanation("There is user already registered with cush email").parseJson)
            case Some(str) if str == "Success" => complete(status(str))
            case None => complete(status("Failure").parseJson)
          }
        }
      } ~ path("signIn") {
        entity(as[LoginPassword]) { credentials =>
          val res: Future[Option[Seq[Task]]] = signIn(credentials.login, credentials.password)
          onSuccess(res) {
            case Some(seq) => complete(taskListWithStat("Success", seq).parseJson)
            case None =>
              complete(failureWithExplanation("There is no user with such username(e-mail) " +
                "or your password is not correct")
                .parseJson)
          }
        }
      }
    }
}

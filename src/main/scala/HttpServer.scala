import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import http.AuthRoute
import model.db.{TaskTable, UserTable}
import services.{AuthServise, TaskService, UserService}
import slick.lifted.TableQuery
import utils.DataBase.dataBase
import utils.JsonFormaters
import utils.Config

import scala.io.StdIn

object HttpServer extends App with JsonFormaters with Config {

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val users = TableQuery[UserTable]
  val tasks = TableQuery[TaskTable]
  val db = dataBase

  val t = new TaskService(db, tasks)
  val u = new UserService(db, users)
  val a = new AuthServise(db, u, t)

  val authService = new AuthRoute(a)

  val route: Route = authService.route

  val bindingFuture = Http().bindAndHandleAsync(Route.asyncHandler(route), httpHost, httpPort)

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done
}
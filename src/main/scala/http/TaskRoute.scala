package http

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import services.TaskService
import utils.JsonFormaters
import model.Task
import spray.json._
import scala.concurrent.{ExecutionContext, Future}

class TaskRoute(val taskService: TaskService)(implicit executionContext: ExecutionContext) extends JsonFormaters {
  import utils.RespStatUtil._
  import taskService._

  val route: Route =
    get {
      path("task") {
        parameter("taskName".as[String]) { taskName =>
          val res: Future[Option[Task]] = findTaskByTitle(taskName)
          onSuccess(res) {
            case Some(task) => complete(taskWithStat("Success", task).parseJson)
            case None => complete(status("Failure"))
          }
        } ~
          parameter("taskName".as[Int]) { taskId =>
            val res: Future[Option[Task]] = findTaskById(taskId)
            onSuccess(res) {
              case Some(task) => complete(taskWithStat("Success", task).parseJson)
              case None => complete(status("Failure"))
            }
          } ~
          pathPrefix("delete") {
            parameter("taskId".as[Int]) { id =>
              deleteTaskById(id)
              complete(status("Success"))
            } ~
              parameter("taskTitle".as[String]) { taskTitle =>
              deleteTaskByTitle(taskTitle)
              complete(status("Success"))
            }
          } ~
        pathPrefix("create") {
          entity(as[Task]) { newTask =>
            val res = createTask(newTask)
            onSuccess(res) {
              case Some(str) => complete(status("Success"))
              case None => complete(failureWithExplanation("There is already task with such title"))
            }
          }
        }
      }
   } ~
  put {
    path("taskUpdate") {
      parameter("taskName".as[String]) { taskTitle =>
        entity(as[Task]) { newTask =>
          updateTaskWithTitle(taskTitle, newTask)
          complete(status("Success"))
        } ~
          parameter("taskId".as[Int]) { taskId =>
            entity(as[Task]) { newTask =>
              updateTaskWithId(taskId, newTask)
              complete(status("Success"))
            }
          }
      }
    }
  }
}

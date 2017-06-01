
import slick.jdbc.MySQLProfile.api._
import slick.lifted.TableQuery

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global
import model._
import model.db.{TaskTable, UserTable}
import services._
import akka.actor.ActorSystem
import utils.DataBase._

//curl -H 'Content-Type: application/json' -X PUT -d '{"id":1,"username":"Vova","email":"vova1997@gmail.com","passwd":"123roqwe"}' http://localhost:8080/signUp


object Playground extends App {

  val users = TableQuery[UserTable]
  val tasks = TableQuery[TaskTable]
  val db = dataBase

  val dropCmd = DBIO.seq(users.schema.drop)

  val setup = DBIO.seq(users += User(1, "Mike", "mike9n@gmail.com", "23ewdwq563"))

  def runQuery = {

    val queryFuture = Future {
      db.run(users.result).map(_.foreach {
        case user: User => println(s"${user.username}: ${user.id} : ${user.email}")
      })
    }

    Await.result(queryFuture, Duration.Inf)
  }

  val t = new TaskService(db, tasks)
  val u = new UserService(db, users)
  val a = new AuthServise(db, u, t)

  val result = a.signIn("ann1975ann@gmail.com", "1223563")

  Await.result(result, Duration.Inf)

  println(result)

  db.close
}

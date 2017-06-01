package services

import model.Task
import model.db.TaskTable
import slick.lifted.TableQuery
import slick.jdbc.MySQLProfile.api._
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future
/**
  * Created by vovanrew on 14.05.17.
  */
class TaskService(db: Database, val tasks: TableQuery[TaskTable]) {

  def createTask(task: Task): Future[Option[String]] = {
    db.run(tasks.filter(_.title === task.title).result).flatMap {
      case seq => {
        if (seq.isEmpty)
          Future.successful(None)
        else {
          db.run(tasks += task)
          Future.successful(Some("Success"))
        }
      }
    }
  }

  def deleteTask(id: Int) = db.run(tasks.filter(_.id === id).delete)

  def updateTaskWithId(id: Int, newTask: Task) = {
    db.run(tasks.filter(_.id === id)
      .map(taskToUpdate => (taskToUpdate.title, taskToUpdate.text))
      .update((newTask.title, newTask.text)))
  }

  def updateTaskWithTitle(title: String, newTask: Task) = {
    db.run(tasks.filter(_.title === title)
      .map(taskToUpdate => (taskToUpdate.title, taskToUpdate.text))
      .update((newTask.title, newTask.text)))
  }

  def completeTaskById(id: Int) = {
    db.run(tasks.filter(_.id === id)
      .map(taskToUpdate => taskToUpdate.done)
      .update(true))
  }

  def completeTaskByTitle(title: String) = {
    db.run(tasks.filter(_.title === title)
      .map(taskToUpdate => taskToUpdate.done)
      .update(true))
  }

  def findTaskByTitle(title: String): Future[Option[Task]] = {
    db.run(tasks.filter(_.title === title).result.headOption)
  }

  def findTaskById(id: Int): Future[Option[Task]] = {
    db.run(tasks.filter(_.id === id).result.headOption)
  }

  def deleteTaskByTitle(title: String) =
    db.run(tasks.filter(_.title === title).delete)


  def deleteTaskById(id: Int) =
    db.run(tasks.filter(_.id === id).delete)
}

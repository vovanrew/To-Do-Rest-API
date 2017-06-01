package utils

import model.Task

/**
  * Created by vovanrew on 19.05.17.
  */
object RespStatUtil {
  def status(stat: String): String = {
    s"""{ "Status" : "$stat" }"""
  }

  def jsonForm(task: Task): String = {
    s"""{"id" : "${task.id}", "title" : "${task.title}", "text" : "${task.id}", "done" : "${task.done}", "user" : ${task.id}}, """
  }

  def taskWithStat(stat: String, task: Task): String =
    s"""{ "Status" : "$stat", "content" : ${jsonForm(task)}}"""

  def taskListWithStat(stat: String, seq: Seq[Task]): String = {


    def loop(acc: String, s: Seq[Task]): String = {
      if(s.isEmpty) "[" + acc.substring(0, acc.length - 2) + "]"
      else {
        loop(acc = acc + jsonForm(s.head), s.tail)
      }
    }

    s"""{ "Status" : "$stat", "content" : ${loop("", seq)} }"""
  }

  def failureWithExplanation(expl: String): String = {
    s"""{ "status" : "Failure", "explanation" : "$expl"}"""
  }
}

package model

/**
  * Created by vovanrew on 13.05.17.
  */
final case class Task(id: Int = 1, title: String, text: String = "", done: Boolean = false, user: Int) {
  require(!title.isEmpty, "title.empty")
}
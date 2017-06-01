package utils

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import model.{Task, User, LoginPassword}
import spray.json.DefaultJsonProtocol

/**
  * Created by vovanrew on 14.05.17.
  */

trait JsonFormaters extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val logPassJsonFormat = jsonFormat2(LoginPassword)
  implicit val userJsonFormat = jsonFormat4(User)
  implicit val taskJsonFormat = jsonFormat5(Task)
}

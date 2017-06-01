import spray.json._
import DefaultJsonProtocol._
import model.User

implicit val userJsonSupport = jsonFormat4(User)

println(User(1, "Vova", "vova1997@gmail.com", "123roqwe").toJson)
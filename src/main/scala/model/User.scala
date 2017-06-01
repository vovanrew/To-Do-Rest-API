package model

/**
  * Created by vovanrew on 13.05.17.
  */

final case class User(id: Int, username: String, email: String, passwd: String) {
  require(!username.isEmpty, "name.empty")
  require(!email.isEmpty, "email.empty")
  require(!passwd.isEmpty, "passwd.empty")
}
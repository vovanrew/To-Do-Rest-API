package model

/**
  * Created by vovanrew on 19.05.17.
  */
case class LoginPassword(login: String, password: String) {
  require(!login.isEmpty, "login.empty")
  require(!password.isEmpty, "password.empty")
}

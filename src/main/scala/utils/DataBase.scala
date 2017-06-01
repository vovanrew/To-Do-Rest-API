package utils

import slick.jdbc.MySQLProfile.api._

object DataBase extends Config {

  val dataBase = Database.forURL(
    url = jdbcUrl,
    user = dbUser,
    password = dbPassword,
    driver = dbDriver,
    keepAliveConnection = keepAlive
  )
}

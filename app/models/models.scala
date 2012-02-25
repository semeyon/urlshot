package models
 
import play.api.db._
import play.api.Play.current
 
import anorm._
import anorm.SqlParser._

case class Url(id: Pk[Long], url: String, counter: Int)

object Url{

    val simple = {
        get[Pk[Long]]("id") ~ 
        get[String]("url")  ~
        get[Int]("counter") map{
            case id~url~counter => Url(id, url, counter)
        }
    }
    
}

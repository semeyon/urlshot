package models
 
import play.api.db._
import play.api.Play.current
 
import anorm._
import anorm.SqlParser._

case class Url(id:  Pk[Long] = NotAssigned, 
               url: String, 
               counter: Int = 0)

object Url{
    
    // -- Parsers
   /*
    * Url pattern
    */
    val pattern = """(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&amp;:/~\+#]*[\w\-\@?^=%&amp;/~\+#])?""".r
    
    /**
    * Parse a url from a ResultSet
    */
   
    val simple = {
        get[Pk[Long]]("id") ~ 
        get[String]("url")  ~
        get[Int]("counter") map{
            case id~url~counter => Url(id, url, counter)
        }
    }
    
    // -- Queries
    /**
    * Retrieve a list of all yrl from db.
    */
    def findAll(): Seq[Url] = {
        DB.withConnection {
            implicit connection =>
            SQL("select * from url").as(Url.simple *)
        }
    }
    
    /**
    * Retrieve an url from the id.
    */
    def getById(id: Long): Option[Url] = {
       DB.withConnection {
            implicit connection =>
            SQL("select * from url where id = {id}").on(
                'id -> id
            ).as(Url.simple.singleOpt)
        } 
    }
    
    /**
    * Retrive an url by the acual url
    */
    def getByUrl(url: String): Option[Url] = {
       DB.withConnection {
            implicit connection =>
            SQL("select * from url where url = {url}").on(
                'url -> url
            ).as(Url.simple.singleOpt)
        } 
    }
    
    /**
    * Create a url in a db.
    */
    def create(url: Url): Unit = {
        DB.withConnection {
            implicit connection =>
            SQL("insert into url(url) values ({url})").on(
                'url -> url.url
            ).executeUpdate()
        }
    }
    
    /**
    * Update a url in a db.
    */
    def incCounter(id: Int): Unit = {
        DB.withConnection {
            implicit connection =>
            SQL("""
                update url 
                set 
                    counter=counter+1 
                where id={id}
                """).on(
            "id"  -> id
            ).executeUpdate()
        }
    }
    
}

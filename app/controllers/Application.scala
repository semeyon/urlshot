package controllers

import play.api._
import play.api.mvc._
import converter._
object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
  
  def shotUrl = Action{Ok("NotImplemented")}
  
  def getUrl(abc: String) = Action {
      val id = Converter.decode(abc)
      Ok("NotImplemented " + id toString)
  }
  
  def clearUrl = Action {Ok("NotImplemented")}
}

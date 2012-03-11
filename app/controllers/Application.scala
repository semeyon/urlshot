package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import converter._
import models.Url

object Application extends Controller {
  
    /*
     * Submit url form
     */
    
     val urlForm = Form(
        mapping(
            "url" -> nonEmptyText //TODO: Change to text verifying(pattern())
        )((url) => Url(url=url))((url:Url) => Some(url.url))
    )

    def index = Action {
      Ok(views.html.index(urlForm))
    }
    
    def shotUrl = Action{ 
        implicit request =>
            urlForm.bindFromRequest.fold(
                errors => BadRequest(views.html.index(errors)), //TODO: in form
                url    => {
                    Url.create(url)
                    val urlWithId = Url.getByUrl(url.url)
                    val postfix   = Converter.encode(urlWithId.id.toString.toInt) //Yes! It's agly!
                    val shortUrl = Url(url=postfix)
                    Ok(views.html.shot(shortUrl))
                }
            )
    }
    
    def getUrl(abc: String) = Action {
        val id  = Converter.decode(abc)
        val url = Url.getById(id)
        
        Redirect(url.url, status = MOVED_PERMANENTLY)
    }
    
    def clearUrl = TODO
        
}

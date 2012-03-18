package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._

import converter._
import models.Url

object Application extends Controller {
  
    /*
     * Submit url form.
     */
    
     val urlForm = Form(
        mapping(
            "url" -> nonEmptyText.verifying(pattern(Url.pattern, 
                                     error="It's must be url!"))
        )((url) => Url(url=url))((url:Url) => Some(url.url))
    )
    
    /*
     * Indes page.
     */
    def index = Action {
      Ok(views.html.index(urlForm))
    }
    
    /*
     * Create short url action.
     */
    def shotUrl = Action{ 
        implicit request =>
            urlForm.bindFromRequest.fold(
                errors => BadRequest(views.html.index(errors)), //TODO: in form
                url    => {
                    val urlWithId = Url.getByUrl(url.url) match {
                        case Some(u) => u 
                        case None    => { 
                            Url.create(url)
                            Url.getByUrl(url.url) get
                        }
                    }
                    val postfix   = Converter.encode(urlWithId.id.toString.toInt) //Yes! It's agly! TODO:!
                    val shortUrl  = "http://%s/%s".format(request.host, postfix)
                    Ok(views.html.shot(shortUrl))
                }
            )
    }
    
     
    /*
     * Found and redirect to real url.
     */
    def getUrl(abc: String) = Action {
        val id  = Converter.decode(abc)
        Url.getById(id) match {
            case Some(u) => {
                Url.incCounter(id)
                Redirect(u.url, status = MOVED_PERMANENTLY)
            }
            case None => NotFound(views.html.notfound())
        }
    }
     
    /*
     * Same as shotUrl method, but instead html response it'll return 
     * in plain text.
     */
    def clearUrl = TODO
        
}

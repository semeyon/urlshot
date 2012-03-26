package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import play.api.libs.ws._

class ApplicationSpec extends Specification {
    
    val inMD = inMemoryDatabase()
     
    "Application" should {
        
        "return form on /" in {
            val result = controllers.Application.index(FakeRequest())
            
            status(result) must equalTo(OK)

            contentAsString(result) must contain("""<title>URLshot</title>""")
            contentAsString(result) must contain("""<form action="/" method="POST" id="urlForm" class="well form-inline">""")
        }
        
        "return short url on POST long url" in {
            running(FakeApplication(additionalConfiguration = inMD)) {
                val result = controllers.Application.shotUrl(
                    FakeRequest().withFormUrlEncodedBody("url" -> "http://ixbt.com/")
                )
                status(result) must equalTo(OK)
                contentAsString(result) must contain("""http:///b""")
            }
        }

        "return errors on empty input data" in {
            running(FakeApplication(additionalConfiguration = inMD)) {
                val result = controllers.Application.shotUrl(
                    FakeRequest().withFormUrlEncodedBody("url" -> "")
                )
                status(result) must equalTo(BAD_REQUEST)
                contentAsString(result) must contain("""It's must be url!""")
                contentAsString(result) must contain("""This field is required""")
            }
        }
        
        "return errors on wrong input data" in {
            running(FakeApplication(additionalConfiguration = inMD)) {
                val result = controllers.Application.shotUrl(
                    FakeRequest().withFormUrlEncodedBody("url" -> "ixbt.com")
                )
                status(result) must equalTo(BAD_REQUEST)
                contentAsString(result) must contain("""It's must be url!""")
            }
        }
        
        "redirect to real url" in {
            running(FakeApplication(additionalConfiguration = inMD)) {
               controllers.Application.shotUrl(
                    FakeRequest().withFormUrlEncodedBody("url" -> "http://ixbt.com/")
               )
               val redirect = controllers.Application.getUrl("b")(FakeRequest())
               
               status(redirect) must equalTo(MOVED_PERMANENTLY)
               redirectLocation(redirect) must equalTo(Some("http://ixbt.com/"))
            }
        }

        "raise not found if url does not exists" in {
            running(FakeApplication(additionalConfiguration = inMD)) {
                val redirect = controllers.Application.getUrl("b")(FakeRequest())

                status(redirect) must equalTo(NOT_FOUND)
                contentAsString(redirect) must contain("""Url is Not Found!""")
            }
        }
     
        "return short url on POST long url via REST" in {
            running(FakeApplication(additionalConfiguration = inMD)) {
                val result = controllers.Application.clearUrl(
                    FakeRequest().withFormUrlEncodedBody("url" -> "http://ixbt.com/")
                )
                status(result) must equalTo(OK)
                contentAsString(result) must contain("""http:///b""")
            }
        }

        "return errors on empty input data via REST" in {
            running(FakeApplication(additionalConfiguration = inMD)) {
                val result = controllers.Application.clearUrl(
                    FakeRequest().withFormUrlEncodedBody("url" -> "")
                )
                status(result) must equalTo(BAD_REQUEST)
                contentAsString(result) must contain("""It's must be url!""")
                contentAsString(result) must contain("""error.required""")
            }
        }
        
        "return errors on wrong input data via REST" in {
            running(FakeApplication(additionalConfiguration = inMD)) {
                val result = controllers.Application.clearUrl(
                    FakeRequest().withFormUrlEncodedBody("url" -> "ixbt.com")
                )
                status(result) must equalTo(BAD_REQUEST)
                contentAsString(result) must contain("""It's must be url!""")
            }
        }
        
    }
}

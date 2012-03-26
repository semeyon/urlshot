package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import play.api.libs.ws._

class ApplicationFuncSpec extends Specification {

    "Application(Functional tests)" should {

        "get /" in {
            running(TestServer(3333)) {
                val fut = WS.url("http://localhost:3333").get
                
                await(fut).status must equalTo(OK)
                await(fut).body must contain("""<title>URLshot</title>""")
                await(fut).body must contain("""<form action="/" method="POST" id="urlForm" class="well form-inline">""") 
            }
        }
    }
}
